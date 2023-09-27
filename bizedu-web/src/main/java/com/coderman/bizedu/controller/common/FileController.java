package com.coderman.bizedu.controller.common;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.resolver.IpKeyResolver;
import com.coderman.limiter.annotation.RateLimit;
import com.coderman.oss.enums.FileModuleEnum;
import com.coderman.oss.util.AliYunOssUtil;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zhangyukang
 * @date ：2023/09/27 10:20
 */
@Api(value = "文件上传接口", tags = "文件上传")
@RestController
@Slf4j
public class FileController {

    @Resource
    private AliYunOssUtil aliYunOssUtil;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "文件上传")
    @PostMapping(value = "/file/upload")
    @RateLimit(replenishRate = 2 , burstCapacity = 1,  keyResolver = IpKeyResolver.class, timeUnit = TimeUnit.HOURS)
    public ResultVO<String> uploadFile(@RequestPart(value = "file", required = false) MultipartFile file, @RequestParam(value = "fileModule", defaultValue = "common") String fileModule) {

        final String fileDomain = "https://ioss-bucket.oss-cn-shenzhen.aliyuncs.com/";

        if(Objects.isNull(file) || file.isEmpty()){

            return ResultUtil.getWarn("上传的文件不能空！");
        }

        try {
            // 获取文件路径
            String filePath = this.aliYunOssUtil.genFilePath(file.getOriginalFilename(), FileModuleEnum.codeOf(fileModule));
            // 上传文件
             this.aliYunOssUtil.uploadStreamIfNotExist(file.getInputStream(), filePath);

            return ResultUtil.getSuccess(String.class, fileDomain + filePath);

        } catch (Exception e) {

            log.error("上传文件失败,file:{},fileModule", fileModule, e);
            return ResultUtil.getFail("上传文件失败！");
        }
    }
}
