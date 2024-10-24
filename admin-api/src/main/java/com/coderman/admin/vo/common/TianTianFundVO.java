package com.coderman.admin.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：zhangyukang
 * @date ：2024/10/24 14:33
 */
@Data
public class TianTianFundVO {

    @ApiModelProperty(value = "基金代码")
    private String fundCode;

    @ApiModelProperty(name = "基金名称")
    private String fundName;

    @ApiModelProperty(value = "净值日期")
    private String netValueDate;

    @ApiModelProperty(value = "当日净值")
    private String currentNetValue;

    @ApiModelProperty(value = "估算净值")
    private String estimatedNetValue;

    @ApiModelProperty(value = "估算涨跌百分比")
    private String estimatedChangePercent;

    @ApiModelProperty(value = "估值时间")
    private String estimatedTime;

    @ApiModelProperty(value = "持仓成本价")
    private String holdingCostPrice;

    @ApiModelProperty(value = "持有份额")
    private String holdingShares;

    @ApiModelProperty(value = "收益率")
    private String yieldRate;

    @ApiModelProperty(value = "总收益")
    private String totalIncome;

    @ApiModelProperty(value = "今日收益")
    private String todayIncome;

}
