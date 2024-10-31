package com.coderman.admin.vo.common;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zhangyukang
 * @date ：2024/10/31 9:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationCountVO extends BaseModel {

    @ApiModelProperty(value = "消息通知类型")
    private String notificationType;

    @ApiModelProperty(value = "未读数量")
    private Long unReadCount;
}
