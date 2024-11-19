package com.coderman.admin.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：zhangyukang
 * @date ：2024/10/17 17:36
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "发送人id (为空则为系统发送)")
    private Integer senderId;

    @ApiModelProperty(value = "指定token会话")
    private String sessionKey;

    @ApiModelProperty(value = "消息类型")
    private String type;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "消息内容")
    private String message;

    @ApiModelProperty(value = "跳转url")
    private String url;

    @ApiModelProperty(value = "是否弹框提示")
    private Boolean isPop;
}
