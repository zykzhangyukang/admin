package com.coderman.admin.service.common;

import com.coderman.admin.dto.common.FileChunkDTO;
import com.coderman.admin.vo.common.UploadChunkStartVO;
import com.coderman.api.vo.ResultVO;

public interface FileService {
    /**
     * 文件分片上传开始
     *
     * @param fileName 文件名
     * @param fileHash 文件hash
     * @param totalParts 分片总数
     * @return
     */
    ResultVO<UploadChunkStartVO> uploadChunkStart(String fileName, String fileHash, Integer totalParts);

    /**
     * 文件分片上传
     *
     * @param fileChunkDTO
     * @return
     */
    ResultVO<Void> uploadChunk(FileChunkDTO fileChunkDTO);

    /**
     * 文件分片上传完成
     * @param fileHash
     * @return
     */
    ResultVO<String> uploadChunkFinish(String fileHash);
}
