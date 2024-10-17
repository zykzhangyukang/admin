package com.coderman.admin.dto.common;

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
public class NotificationDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

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
