package com.coderman.admin.sync.model;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author ：zhangyukang
 * @date ：2024/08/07 17:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlanHistoryModel extends BaseModel {

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "上次变更类型")
    private String lastContent;

    @ApiModelProperty(value = "本次变更内容")
    private String thisContent;

    @ApiModelProperty(value = "操作人")
    private String username;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
