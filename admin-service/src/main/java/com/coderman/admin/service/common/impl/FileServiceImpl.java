package com.coderman.admin.service.common.impl;

import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.UploadPartResult;
import com.coderman.admin.constant.FileConstant;
import com.coderman.admin.dto.common.FileChunkDTO;
import com.coderman.admin.service.common.FileService;
import com.coderman.admin.vo.common.UploadChunkStartVO;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.oss.enums.FileModuleEnum;
import com.coderman.oss.util.AliYunOssUtil;
import com.coderman.redis.service.RedisService;
import com.coderman.service.anntation.LogError;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @author ：zhangyukang
 * @date ：2024/09/26 10:01
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private RedisService redisService;

    private String getUploadId(String fileHash) {
        return this.redisService.getHash("upload:" + fileHash, "uploadId", String.class, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private Set<Integer> getUploadedParts(String fileHash) {
        return this.redisService.getSet("upload:" + fileHash + ":parts", Integer.class, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private void saveUploadMeta(String fileHash, String uploadId, String fileName, Integer totalParts) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("uploadId", uploadId);
        map.put("fileName", fileName);
        map.put("totalParts", totalParts);
        this.redisService.setHash("upload:" + fileHash, map, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private Map<String, Object> getUploadMeta(String fileHash){
        return this.redisService.getMapOfHashAll("upload:" + fileHash, Object.class, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private void markPartUploaded(String fileHash, Integer partNumber, String eTag) {
        this.redisService.addToSet("upload:" + fileHash + ":parts", partNumber, RedisDbConstant.REDIS_DB_DEFAULT);
        this.redisService.setHash("upload:" + fileHash + ":etags", String.valueOf(partNumber), eTag, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    private Map<String,String> getETagMap(String fileHash){
        return this.redisService.getMapOfHashAll("upload:" + fileHash + ":etags", String.class, RedisDbConstant.REDIS_DB_DEFAULT);
    }

    @Override
    @LogError(value = "文件分片上传开始")
    public ResultVO<UploadChunkStartVO> uploadChunkStart(String fileName, String fileHash, Integer totalParts) {

        if (StringUtils.isBlank(fileName) || StringUtils.isBlank(fileHash) || totalParts == null || totalParts <= 0) {
            return ResultUtil.getFail("无效的上传参数");
        }

        UploadChunkStartVO vo = new UploadChunkStartVO();
        try {
            String existingUploadId = this.getUploadId(fileHash);
            if (existingUploadId != null) {
                Set<Integer> uploaded = this.getUploadedParts(fileHash);
                vo.setUploadId(existingUploadId);
                vo.setUploaded(uploaded);
                return ResultUtil.getSuccess(UploadChunkStartVO.class, vo);
            }

            AliYunOssUtil instance = AliYunOssUtil.getInstance();
            String objectName = instance.genStableObjectName(fileName, fileHash, FileModuleEnum.COMMON_MODULE);
            String uploadId = instance.getUploadId(objectName);
            vo.setUploadId(uploadId);
            // 保存元数据
            this.saveUploadMeta(fileHash, uploadId, fileName, totalParts);
        } catch (Exception e) {
            log.error("上传分片初始化失败 fileName：{}，fileHash：{},totalParts:{}", fileName, fileHash, totalParts, e);
            throw new BusinessException("上传分片初始化失败!");
        }

        return ResultUtil.getSuccess(UploadChunkStartVO.class, vo);
    }

    @Override
    @LogError(value = "文件分片上传")
    public ResultVO<Void> uploadChunk(FileChunkDTO dto) {

        String uploadId = dto.getUploadId();
        Integer partNumber = dto.getPartNumber();
        MultipartFile file = dto.getFile();
        String fileHash = dto.getFileHash();
        String fileName = dto.getFileName();

        try {
            AliYunOssUtil instance = AliYunOssUtil.getInstance();
            String objectName = instance.genStableObjectName(fileName, fileHash, FileModuleEnum.COMMON_MODULE);
            UploadPartResult uploadPartResult = instance.uploadPart(file, objectName, uploadId, partNumber);
            this.markPartUploaded(fileHash, partNumber, uploadPartResult.getETag());
        } catch (Exception e) {
            log.error("上传分片失败 uploadId：{}，partNumber：{},objectName:{}", uploadId, partNumber, fileName, e);
            throw new BusinessException("上传分片失败！");
        }

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "文件分片上传完成")
    public ResultVO<String> uploadChunkFinish(String fileHash) {

        if (StringUtils.isBlank(fileHash)) {
            return ResultUtil.getFail("文件Hash不能为空！");
        }

        String uploadId = this.getUploadId(fileHash);
        if (uploadId == null) {
            return ResultUtil.getFail("未找到对应的上传任务！");
        }

        Map<String, Object> uploadMeta = this.getUploadMeta(fileHash);
        String fileName = (String) uploadMeta.get("fileName");
        Integer totalParts = (Integer) uploadMeta.get("totalParts");

        if (fileName == null || totalParts == null) {
            return ResultUtil.getFail("上传元数据不完整！");
        }

        // 2. 获取所有已上传分片的 ETag
        Map<String, String> eTagMap = this.getETagMap(fileHash);
        if (eTagMap.size() != totalParts) {
            return ResultUtil.getFail("分片数量不完整，无法完成上传！");
        }

        // 3. 组装完成分片上传请求
        AliYunOssUtil instance = AliYunOssUtil.getInstance();
        String objectName = instance.genStableObjectName(fileName, fileHash, FileModuleEnum.COMMON_MODULE);
        CompleteMultipartUploadResult uploadResult = instance.completeMultipartUpload(objectName, uploadId, eTagMap);
        log.info("分片上传完成:{},fileHash:{}",uploadResult.getLocation(),fileHash);
        return ResultUtil.getSuccess(String.class, FileConstant.OSS_FILE_DOMAIN + objectName);
    }
}
