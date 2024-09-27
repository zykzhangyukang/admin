package com.coderman.admin.service.common.impl;

import com.coderman.admin.dto.common.FileChunkDTO;
import com.coderman.admin.service.common.FileService;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.oss.enums.FileModuleEnum;
import com.coderman.oss.util.AliYunOssUtil;
import com.coderman.service.anntation.LogError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：zhangyukang
 * @date ：2024/09/26 10:01
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    @LogError(value = "文件分片上传开始")
    public ResultVO<String> uploadChunkStart(String fileName) {

        if(StringUtils.isBlank(fileName)){
            return ResultUtil.getWarn("参数错误!");
        }

        AliYunOssUtil instance = AliYunOssUtil.getInstance();
        String objectName = instance.genFilePath(fileName, FileModuleEnum.COMMON_MODULE);
        String uploadId = instance.getUploadId(objectName);

        return ResultUtil.getSuccess(String.class, uploadId);
    }

    @Override
    @LogError(value = "文件分片上传")
    public ResultVO<Void> uploadChunk(FileChunkDTO dto) {

        String uploadId = dto.getUploadId();
        Integer chunkNumber = dto.getChunkNumber();
        MultipartFile filePart = dto.getFilePart();

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "文件分片上传完成")
    public ResultVO<String> uploadChunkFinish(String uploadId) {
        return null;
    }
}
