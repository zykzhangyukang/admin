package com.coderman.admin.controller.sync;

import com.coderman.admin.sync.dto.ResultPageDTO;
import com.coderman.admin.sync.dto.ResultValidDataDTO;
import com.coderman.admin.sync.model.ResultModel;
import com.coderman.admin.utils.AuthUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import com.coderman.admin.sync.service.ResultService;
import com.coderman.admin.sync.vo.CompareVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
@Api(value = "同步记录", tags = "同步记录")
@RestController
@RequestMapping(value = "/sync/result")
public class ResultController {

    @Resource
    private ResultService resultService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "同步记录查询")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"pageRow", "totalRow", "currPage", "totalPage", "dataList"}),
            @ApiReturnParam(name = "ResultModel", value = {"syncToEs", "msgSrc", "syncTime", "errorMsg", "uuid", "planCode", "msgId", "planName", "mqId", "planUuid", "status", "repeatCount", "msgCreateTime",
                    "msgContent", "destProject", "srcProject", "syncContent", "remark","hlsMsgContent","hlsSyncContent"})
    })
    public com.coderman.api.vo.ResultVO<PageVO<List<ResultModel>>> page(@RequestBody ResultPageDTO resultPageDTO) throws Exception {
        return this.resultService.page(resultPageDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "重新同步")
    @PutMapping(value = "/repeat/sync")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "uuid", required = true),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public com.coderman.api.vo.ResultVO<Void> repeatSync(String uuid) {

        return this.resultService.repeatSync(uuid);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "手动标记成功")
    @PutMapping(value = "/sign/success")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "uuid", required = true),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public com.coderman.api.vo.ResultVO<Void> signSuccess(String uuid) throws IOException {
        String msg = String.format("%s于%s,手动标记成功", Objects.requireNonNull(AuthUtil.getCurrent()).getRealName() , DateFormatUtils.format(new Date(),"yyyy年MM月dd日HH时mm分ss秒"));
        return this.resultService.signSuccess(uuid, msg);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "校验同步结果")
    @PostMapping(value = "/valid/data")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "CompareVO", value = {"srcUnique", "destColumnList", "srcResultList", "destResultList", "srcColumnList", "destUnique", "destTable", "srcTable"}),
    })
    public com.coderman.api.vo.ResultVO<List<CompareVO>> validResultData(@RequestBody ResultValidDataDTO resultValidDataDTO) throws Throwable {

        String msgContent = resultValidDataDTO.getMsgContent();
        Assert.notNull(msgContent, "msgContent is null");

        return this.resultService.selectTableData(msgContent, true);
    }

}
