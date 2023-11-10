package com.coderman.admin.controller.log;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dto.log.LogPageDTO;
import com.coderman.admin.service.log.LogService;
import com.coderman.admin.vo.log.LogVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2023/09/27 17:36
 */
@Api(value = "日志管理", tags = "日志管理")
@RestController
@RequestMapping(value = "/auth/log")
public class LogController {

    @Resource
    private LogService logService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "日志列表")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "pageRow", "totalRow", "currPage", "totalPage"}),
            @ApiReturnParam(name = "LogVO", value = {"username", "logModule", "userId", "createTime", "logId", "logInfo", "logLevel", "realName"})
    })
    public ResultVO<PageVO<List<LogVO>>> page(@RequestBody LogPageDTO logPageDTO) {

        return this.logService.page(logPageDTO);
    }

}
