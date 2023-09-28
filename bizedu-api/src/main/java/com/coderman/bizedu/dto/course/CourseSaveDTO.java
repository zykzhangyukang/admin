package com.coderman.bizedu.dto.course;

import com.coderman.api.model.BaseModel;
import com.coderman.bizedu.vo.catalog.CatalogVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseSaveDTO extends BaseModel {

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "创建人id")
    private Integer creatorId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "课程分类")
    private List<Integer> catalogIdList;
}
