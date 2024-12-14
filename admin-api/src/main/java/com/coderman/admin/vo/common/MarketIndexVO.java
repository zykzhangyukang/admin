package com.coderman.admin.vo.common;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class MarketIndexVO extends BaseModel {

    @ApiModelProperty(value = "指数名称")
    private String indexName;
    @ApiModelProperty(value = "指数")
    private BigDecimal index;
    @ApiModelProperty(value = "指数变更")
    private BigDecimal changeVal;
    @ApiModelProperty(value = "指数变化率")
    private BigDecimal changeRate;

}
