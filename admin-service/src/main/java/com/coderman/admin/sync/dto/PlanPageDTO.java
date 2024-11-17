package com.coderman.admin.sync.dto;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlanPageDTO extends BaseModel {

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "同步计划编号")
    private String planCode;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "源数据库")
    private String srcDb;

    @ApiModelProperty(value = "目标数据库")
    private String destDb;

    @ApiModelProperty(value = "源系统")
    private String srcProject;

    @ApiModelProperty(value = "目标系统")
    private String destProject;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "排序规则")
    private String sortType;

}
