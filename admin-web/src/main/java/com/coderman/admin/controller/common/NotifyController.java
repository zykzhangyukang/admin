package com.coderman.admin.controller.common;

import com.coderman.admin.dto.common.NotificationPageDTO;
import com.coderman.admin.service.common.NotificationService;
import com.coderman.admin.vo.common.NotificationVO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2024/10/16 16:53
 */
@Api(value = "消息通知", tags = "消息通知")
@RestController
@RequestMapping(value = "/common/notification")
public class NotifyController {

    @Resource
    private NotificationService notificationService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取未读消息数")
    @GetMapping(value = "/count")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Long> getUnReadCount() {
        return this.notificationService.getUnReadCount();
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "获取消息列表")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO",value = {"pageRow", "totalRow", "currPage", "totalPage", "dataList"}),
            @ApiReturnParam(name = "NotificationVO", value = {"notificationId", "notificationType", "userId","isRead","createTime","data","message"}),
    })
    public ResultVO<PageVO<List<NotificationVO>>> getNotificationPage(@RequestBody NotificationPageDTO notificationPageDTO) {
        return this.notificationService.getNotificationPage(notificationPageDTO);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "标记已读")
    @PutMapping(value = "/read")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Void> maskNotificationRead(Integer notificationId) {
        return this.notificationService.updateNotificationRead(notificationId);
    }
}
