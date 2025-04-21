package com.coderman.admin.service.common;

import com.coderman.admin.dto.common.FileChunkDTO;
import com.coderman.admin.dto.common.FilePreviewDTO;
import com.coderman.admin.vo.common.UploadChunkInitVO;
import com.coderman.api.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    /**
     * 普通文件上传
     * @param file 文件
     * @param fileModule 模块
     * @return
     */
    ResultVO<String> uploadFile(MultipartFile file, String fileModule) throws Exception;

    /**
     * 文件分片上传开始
     *
     * @param fileName 文件名
     * @param fileHash 文件hash
     * @param totalParts 分片总数
     * @return
     */
    ResultVO<UploadChunkInitVO> uploadChunkInit(String fileName, String fileHash, Integer totalParts);

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

    /**
     * 文件预览
     * @param fileUrl
     * @param response
     */
    public void switchToPdf(FilePreviewDTO filePreviewDTO, HttpServletResponse response) throws Exception;
}
