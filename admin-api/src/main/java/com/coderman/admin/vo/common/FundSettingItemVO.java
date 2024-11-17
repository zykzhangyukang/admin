package com.coderman.admin.vo.common;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class FundSettingItemVO extends BaseModel {

    @ApiModelProperty(value = "基金编号")
    private String fundCode;

    @ApiModelProperty(value = "持仓成本价")
    private BigDecimal costPrise;

    @ApiModelProperty(value = "持有份额")
    private BigDecimal bonds;
}
