package com.coderman.admin.controller.sync;

import com.coderman.admin.sync.dto.CallbackPageDTO;
import com.coderman.admin.sync.dto.CallbackRepeatDTO;
import com.coderman.admin.sync.model.CallbackModel;
import com.coderman.admin.sync.service.CallbackService;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
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
 * @author zhangyukang
 */
@Api(value = "消息回调", tags = "消息回调")
@RestController
@RequestMapping(value = "/sync/callback")
public class CallbackController {

    @Resource
    private CallbackService callbackService;

    @ApiOperation(value = "消息回调列表",httpMethod = SwaggerConstant.METHOD_POST)
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO",value = {"pageRow", "totalRow", "currPage", "totalPage", "dataList"}),
            @ApiReturnParam(name = "CallbackModel",value = {"srcProject", "msgContent", "createTime", "destProject", "sendStatus", "dealStatus", "dealCount",
                    "sendTime", "ackTime", "mid","remark","errorMsg", "uuid", "status", "repeatCount","msgId"})
    })
    public ResultVO<PageVO<List<CallbackModel>>> selectCallbackPage(@RequestBody CallbackPageDTO callbackPageDTO){

        return this.callbackService.selectCallbackPage(callbackPageDTO);
    }


    @ApiOperation(value = "重新回调",httpMethod = SwaggerConstant.METHOD_POST)
    @PostMapping(value = "/repeat")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Void> repeatCallback(@RequestBody CallbackRepeatDTO callbackRepeatDTO){

        return this.callbackService.repeatCallback(callbackRepeatDTO);
    }



}
