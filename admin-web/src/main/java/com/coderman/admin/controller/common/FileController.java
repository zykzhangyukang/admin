package com.coderman.admin.controller.common;

import com.coderman.admin.dto.common.FileChunkDTO;
import com.coderman.admin.service.common.FileService;
import com.coderman.admin.vo.common.UploadChunkInitVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：zhangyukang
 * @date ：2024/09/25 18:22
 */
@Api(value = "文件上传", tags = "文件上传")
@RestController
@RequestMapping(value = "/common/file")
public class FileController {

    @Resource
    private FileService fileService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "文件预览")
    @GetMapping(value = "/preview")
    public void preview(String fileUrl, HttpServletResponse response) throws Exception {
        this.fileService.preview(fileUrl, response);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "普通文件上传")
    @PostMapping(value = "/upload")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<String> uploadFile(MultipartFile file, String fileModule) throws Exception {
        return this.fileService.uploadFile(file, fileModule);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "分片上传开始")
    @PostMapping(value = "/upload/chunk/init")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UploadChunkInitVO", value = {"uploadId", "uploaded","isSkip","filePath"}),
    })
    public ResultVO<UploadChunkInitVO> uploadChunkInit(String fileName, String fileHash, Integer totalParts) {
        return this.fileService.uploadChunkInit(fileName,fileHash, totalParts);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "分片上传")
    @PostMapping(value = "/upload/chunk")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Void> uploadChunk(FileChunkDTO fileChunkDTO) {
        return this.fileService.uploadChunk(fileChunkDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "分片上传结束")
    @PostMapping(value = "/upload/chunk/finish")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<String> uploadChunkFinish(String fileHash) {
        return this.fileService.uploadChunkFinish(fileHash);
    }

}
