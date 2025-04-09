package com.coderman.admin.controller.common;

import com.coderman.admin.dto.common.FileChunkDTO;
import com.coderman.admin.service.common.FileService;
import com.coderman.admin.vo.common.UploadChunkStartVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

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

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "分片上传开始")
    @PostMapping(value = "/upload/chunk/start")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UploadChunkStartVO", value = {"uploadId", "uploaded"}),
    })
    public ResultVO<UploadChunkStartVO> uploadChunkStart(String fileName, String fileHash, Integer totalParts) {
        return this.fileService.uploadChunkStart(fileName,fileHash, totalParts);
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
