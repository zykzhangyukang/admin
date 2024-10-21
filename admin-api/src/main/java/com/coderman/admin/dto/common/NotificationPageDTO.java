package com.coderman.admin.dto.common;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2024/10/21 10:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationPageDTO  extends BaseModel {

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "消息模块")
    private String module;

    @ApiModelProperty(value = "消息通知类型")
    private List<String> notificationTypes;

    @ApiModelProperty(value = "是否已读 0-未读,1-已读")
    private Integer isRead;

    @ApiModelProperty(value = "排序类型")
    private String sortType;

    @ApiModelProperty(value = "排序字段")
    private String sortField;
}
