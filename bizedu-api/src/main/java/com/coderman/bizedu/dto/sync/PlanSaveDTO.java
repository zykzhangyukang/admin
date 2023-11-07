package com.coderman.bizedu.dto.sync;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlanSaveDTO extends BaseModel {

    @ApiModelProperty(value = "计划内容")
    private String planContent;

}
