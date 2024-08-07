package com.coderman.admin.sync.dto;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zhangyukang
 * @date ：2024/08/07 17:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlanHistoryDTO extends BaseModel {

    @ApiModelProperty(value = "同步计划uuid")
    private String uuid;

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

}
