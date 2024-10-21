package com.coderman.admin.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author ：zhangyukang
 * @date ：2024/10/21 10:23
 */
@Data
public class NotificationVO {

    @ApiModelProperty(value = "主键")
    private Integer notificationId;

    @ApiModelProperty(value = "消息通知类型")
    private String notificationType;

    @ApiModelProperty(value = "被通知人id")
    private Integer userId;

    @ApiModelProperty(value = "是否已读 0-未读,1-已读")
    private Integer isRead;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "消息通知数据(json)")
    private String data;
}
