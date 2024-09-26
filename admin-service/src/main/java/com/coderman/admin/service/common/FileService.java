package com.coderman.admin.service.common;

import com.coderman.admin.dto.common.FileChunkDTO;
import com.coderman.api.vo.ResultVO;

public interface FileService {
    /**
     * 文件分片上传开始
     *
     * @param fileName
     * @return
     */
    ResultVO<String> uploadChunkStart(String fileName);

    /**
     * 文件分片上传
     *
     * @param fileChunkDTO
     * @return
     */
    ResultVO<Void> uploadChunk(FileChunkDTO fileChunkDTO);

    /**
     * 文件分片上传完成
     *
     * @param uploadId
     * @return
     */
    ResultVO<String> uploadChunkFinish(String uploadId);
}
