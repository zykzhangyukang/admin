package com.coderman.admin.controller.sync;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.sync.dto.MessagePageDTO;
import com.coderman.admin.sync.model.MqMessageModel;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import com.coderman.admin.sync.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Api(value = "本地消息", tags = "本地消息")
@RestController
@RequestMapping(value = "/sync/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @ApiOperation(value = "本地消息列表查询",httpMethod = SwaggerConstant.METHOD_POST)
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO",value = {"pageRow", "totalRow", "currPage", "totalPage", "dataList"}),
            @ApiReturnParam(name = "MqMessageModel",value = {"srcProject", "msgContent", "createTime", "destProject", "sendStatus", "dealStatus", "dealCount",
                    "sendTime", "ackTime", "mid","mqMessageId","uuid"})
    })
    public ResultVO<PageVO<List<MqMessageModel>>> selectMessagePage(@RequestBody MessagePageDTO messagePageDTO){

        return this.messageService.selectMessagePage(messagePageDTO);
    }
}
