package com.coderman.admin.controller.notification;

import com.coderman.admin.model.notification.NotificationModel;
import com.coderman.admin.service.notification.NotificationService;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：zhangyukang
 * @date ：2024/10/16 16:53
 */
@Api(value = "消息通知", tags = "消息通知")
@RestController
@RequestMapping(value = "/auth/notification")
public class NotificationController {

    @Resource
    private NotificationService notificationService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取未读消息数")
    @GetMapping(value = "/count")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Long> getNotificationCount() {
        return this.notificationService.getNotificationCount();
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取消息列表")
    @GetMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<PageVO<NotificationModel>> getNotificationPage(String notificationType) {
        return this.notificationService.getNotificationPage(notificationType);
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
