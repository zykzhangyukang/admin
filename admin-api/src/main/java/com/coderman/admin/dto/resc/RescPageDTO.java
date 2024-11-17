package com.coderman.admin.dto.resc;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class RescPageDTO extends BaseModel {

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "资源名称")
    private String rescName;

    @ApiModelProperty(value = "资源url")
    private String rescUrl;

    @ApiModelProperty(value = "功能标识")
    private String funcKey;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "资源所属系统")
    private String rescDomain;

    @ApiModelProperty(value = "请求方式")
    private String methodType;

    @ApiModelProperty(value = "排序类型")
    private String sortType;

    @ApiModelProperty(value = "排序字段")
    private String sortField;
}
