package com.coderman.admin.controller.common;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author ：zhangyukang
 * @date ：2024/09/25 18:22
 */
@Api(value = "文件上传", tags = "文件上传")
@RestController
@RequestMapping(value = "/auth/file")
public class FileController {

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "文件分片上传")
    @PostMapping(value = "/upload/chunk")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "DeptModel",value = {"deptId", "deptCode", "deptName", "createTime","updateTime"})
    })
    public ResultVO<Void> uploadChunk() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return ResultUtil.getSuccess();
    }

}
