package com.coderman.admin.service.common.impl;

import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.UploadPartResult;
import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.SaveFormat;
import com.coderman.admin.constant.FileConstant;
import com.coderman.admin.dao.common.FileDAO;
import com.coderman.admin.dto.common.FileChunkDTO;
import com.coderman.admin.dto.common.FilePreviewDTO;
import com.coderman.admin.model.common.FileExample;
import com.coderman.admin.model.common.FileModel;
import com.coderman.admin.service.common.FileService;
import com.coderman.admin.utils.PdfUtil;
import com.coderman.admin.utils.FileHashUtils;
import com.coderman.admin.vo.common.UploadChunkInitVO;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.oss.enums.FileModuleEnum;
import com.coderman.oss.util.AliYunOssUtil;
import com.coderman.redis.service.RedisService;
import com.coderman.service.anntation.LogError;
import com.coderman.service.util.UUIDUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private RedisService redisService;
    @Resource
    private FileDAO fileDAO;

    private String redisKeyUploadMeta(String fileHash) {
        return "upload:" + fileHash;
    }

    private String redisKeyUploadedParts(String fileHash) {
        return "upload:" + fileHash + ":parts";
    }

    private String redisKeyEtags(String fileHash) {
        return "upload:" + fileHash + ":etags";
    }

    private void saveUploadMeta(String fileHash, String uploadId, String fileName, Integer totalParts) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("uploadId", uploadId);
        map.put("fileName", fileName);
        map.put("totalParts", totalParts);
        redisService.setHash(redisKeyUploadMeta(fileHash), map, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private Map<String, Object> getUploadMeta(String fileHash) {
        return redisService.getMapOfHashAll(redisKeyUploadMeta(fileHash), Object.class, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private void markPartUploaded(String fileHash, Integer partNumber, String eTag) {
        redisService.addToSet(redisKeyUploadedParts(fileHash), partNumber, RedisDbConstant.REDIS_DB_DEFAULT);
        redisService.setHash(redisKeyEtags(fileHash), String.valueOf(partNumber), eTag, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private Set<Integer> getUploadedParts(String fileHash) {
        return redisService.getSet(redisKeyUploadedParts(fileHash), Integer.class, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private Map<String, String> getETagMap(String fileHash) {
        return redisService.getMapOfHashAll(redisKeyEtags(fileHash), String.class, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    @Override
    @Transactional(timeout = 300)
    public ResultVO<String> uploadFile(MultipartFile file, String fileModule) throws Exception {

        // 计算hash
        String fileHash = FileHashUtils.calculateFileHash(file, FileHashUtils.DEFAULT_CHUNK_SIZE);
        FileModel fileModel = this.getFileByHash(fileHash);
        if (fileModel != null) {

            return ResultUtil.getSuccess(String.class, fileModel.getFilePath());
        }

        try {

            String fileName = file.getOriginalFilename();
            AliYunOssUtil instance = AliYunOssUtil.getInstance();
            FileModuleEnum fileModuleEnum = FileModuleEnum.codeOf(fileModule);
            String objectName = instance.genFilePath(fileName, fileModuleEnum);

            instance.uploadStream(file.getInputStream(), objectName);
            fileModel = this.saveFileToDb(fileName, fileHash, FileConstant.OSS_FILE_DOMAIN + objectName);

            return ResultUtil.getSuccess(String.class, fileModel.getFilePath());
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new BusinessException("上传文件失败");
        }
    }

    @Override
    @LogError("初始化分片上传")
    public ResultVO<UploadChunkInitVO> uploadChunkInit(String fileName, String fileHash, Integer totalParts) {
        if (StringUtils.isAnyBlank(fileName, fileHash) || totalParts == null || totalParts <= 0) {
            return ResultUtil.getFail("参数非法");
        }

        UploadChunkInitVO vo = new UploadChunkInitVO();

        // 秒传逻辑
        FileModel fileModel = this.getFileByHash(fileHash);
        if (fileModel != null) {
            vo.setIsSkip(true);
            vo.setFilePath(fileModel.getFilePath());
            return ResultUtil.getSuccess(UploadChunkInitVO.class, vo);
        }

        String existingUploadId = redisService.getHash(redisKeyUploadMeta(fileHash), "uploadId", String.class, RedisDbConstant.REDIS_DB_DEFAULT);
        if (existingUploadId != null) {
            vo.setUploadId(existingUploadId);
            vo.setUploaded(getUploadedParts(fileHash));
            return ResultUtil.getSuccess(UploadChunkInitVO.class, vo);
        }

        try {
            AliYunOssUtil instance = AliYunOssUtil.getInstance();
            String objectName = instance.genStableObjectName(fileName, fileHash, FileModuleEnum.COMMON_MODULE);
            String uploadId = instance.getUploadId(objectName);

            saveUploadMeta(fileHash, uploadId, fileName, totalParts);
            vo.setUploadId(uploadId);
        } catch (Exception e) {
            log.error("初始化失败", e);
            throw new BusinessException("初始化上传失败");
        }

        return ResultUtil.getSuccess(UploadChunkInitVO.class, vo);
    }

    @Override
    @LogError("上传文件分片")
    public ResultVO<Void> uploadChunk(FileChunkDTO dto) {
        String uploadId = dto.getUploadId();
        Integer partNumber = dto.getPartNumber();
        MultipartFile file = dto.getFile();
        String fileHash = dto.getFileHash();
        String fileName = dto.getFileName();

        try {
            AliYunOssUtil oss = AliYunOssUtil.getInstance();
            String objectName = oss.genStableObjectName(fileName, fileHash, FileModuleEnum.COMMON_MODULE);
            UploadPartResult partResult = oss.uploadPart(file, objectName, uploadId, partNumber);

            markPartUploaded(fileHash, partNumber, partResult.getETag());
        } catch (Exception e) {
            log.error("上传失败: uploadId={}, partNumber={}", uploadId, partNumber, e);
            throw new BusinessException("上传失败");
        }

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError("完成文件上传")
    @Transactional(timeout = 300)
    public ResultVO<String> uploadChunkFinish(String fileHash) {
        if (StringUtils.isBlank(fileHash)) {
            return ResultUtil.getFail("文件Hash为空");
        }

        Map<String, Object> meta = getUploadMeta(fileHash);
        String uploadId = (String) meta.get("uploadId");
        String fileName = (String) meta.get("fileName");
        Integer totalParts = (Integer) meta.get("totalParts");

        if (StringUtils.isAnyBlank(uploadId, fileName) || totalParts == null) {
            return ResultUtil.getFail("元数据异常");
        }

        Map<String, String> eTagMap = getETagMap(fileHash);
        if (eTagMap.size() != totalParts) {
            return ResultUtil.getFail("分片缺失，无法合并");
        }

        try {
            AliYunOssUtil oss = AliYunOssUtil.getInstance();
            String objectName = oss.genStableObjectName(fileName, fileHash, FileModuleEnum.COMMON_MODULE);

            List<PartETag> partETags = eTagMap.entrySet().stream()
                    .map(e -> new PartETag(Integer.parseInt(e.getKey()), e.getValue()))
                    .sorted(Comparator.comparingInt(PartETag::getPartNumber))
                    .collect(Collectors.toList());

            CompleteMultipartUploadResult result = oss.completeMultipartUpload(objectName, uploadId, partETags);
            log.info("分片合并结果: {}", result.getETag());

            // 保存文件
            FileModel fileModel = this.saveFileToDb(fileName, fileHash, FileConstant.OSS_FILE_DOMAIN + objectName);

            // 清理数据
            redisService.del(redisKeyUploadMeta(fileHash), RedisDbConstant.REDIS_DB_DEFAULT);
            redisService.del(redisKeyUploadedParts(fileHash), RedisDbConstant.REDIS_DB_DEFAULT);
            redisService.del(redisKeyEtags(fileHash), RedisDbConstant.REDIS_DB_DEFAULT);

            return ResultUtil.getSuccess(String.class, fileModel.getFilePath());
        } catch (Exception e) {
            log.error("合并失败", e);
            throw new BusinessException("文件合并失败！");
        }
    }

    @Override
    public void switchToPdf(FilePreviewDTO filePreviewDTO, HttpServletResponse response) throws Exception {
        String fileName = filePreviewDTO.getCode();
        String suffix = filePreviewDTO.getSuffix().toLowerCase();
        String orgPath = filePreviewDTO.getPath() + fileName;

        // 统一 PDF 存储路径
        String pdfDirPath = Paths.get(getPdfUploadPath(), "pdf").toString();
        File pdfDir = new File(pdfDirPath);
        if (!pdfDir.exists() && pdfDir.mkdirs()) {
            log.info("创建 PDF 文件夹成功: {}", pdfDirPath);
        }

        InputStream fis = null;
        String pdfPath;

        try {
            if ("pdf".equals(suffix)) {
                pdfPath = orgPath;
                fis = getFileInputStream(pdfPath);
            } else {
                String newPdfName = fileName.replaceAll("\\." + suffix + "$", ".pdf");
                pdfPath = Paths.get(pdfDirPath, newPdfName).toString();
                File pdfFile = new File(pdfPath);

                if (!pdfFile.exists()) {
                    pdfPath = convertToPdf(pdfDirPath, newPdfName, suffix, orgPath);
                }

                fis = new FileInputStream(pdfPath);
            }
            writePdfToResponse(fis, fileName, response);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.warn("关闭输入流失败", e);
                }
            }
        }
    }

    private String convertToPdf(String outputDir, String fileName, String suffix, String sourcePath) throws Exception {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = getFileInputStream(sourcePath);
            String fullPdfPath = Paths.get(outputDir, fileName).toString();
            outputStream = new FileOutputStream(fullPdfPath);

            switch (suffix) {
                case "doc":
                case "docx":
                case "html":
                    Document doc = new Document(inputStream);
                    Document result = new Document();
                    result.removeAllChildren();
                    result.appendDocument(doc, ImportFormatMode.USE_DESTINATION_STYLES);
                    result.save(outputStream, SaveFormat.PDF);
                    break;
                case "xls":
                case "xlsx":
                    PdfUtil.excel2pdf(inputStream, outputStream);
                    break;
                default:
                    throw new BusinessException("不支持的文件类型: " + suffix + "，请下载查看。");
            }

            return fullPdfPath;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn("关闭 inputStream 失败", e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.warn("关闭 outputStream 失败", e);
                }
            }
        }
    }

    private void writePdfToResponse(InputStream fis, String fileName, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + ".pdf\"");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            out = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.warn("关闭 response 输出流失败", e);
                }
            }
        }
    }

    private InputStream getFileInputStream(String fileUrl) throws IOException {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            return connection.getInputStream();
        } catch (Exception e) {
            log.error("读取远程文件失败: {}", fileUrl, e);
            throw new FileNotFoundException("文件链接读取失败: " + fileUrl);
        }
    }

    private String getPdfUploadPath() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("windows") ? "E:/home/file" : "/var/java/www/uploadFile";
    }


    private FileModel saveFileToDb(String fileName, String fileHash, String filePath) {
        FileModel fileModel = new FileModel();
        fileModel.setFileKey(UUIDUtils.getPrimaryValue());
        fileModel.setCreateTime(new Date());
        fileModel.setFilePath(filePath);
        fileModel.setFileHash(fileHash);
        fileModel.setFileName(fileName);
        this.fileDAO.insert(fileModel);
        return fileModel;
    }

    private FileModel getFileByHash(String fileHash) {
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andFileHashEqualTo(fileHash);
        List<FileModel> fileModels = this.fileDAO.selectByExample(fileExample);
        if (CollectionUtils.isNotEmpty(fileModels)) {
            return fileModels.get(0);
        }
        return null;
    }
}
