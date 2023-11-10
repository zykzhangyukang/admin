package com.coderman.admin.dto.course;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CoursePageDTO extends BaseModel {

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "创建人")
    private String creatorName;

    @ApiModelProperty(value = "课程状态")
    private String status;

    @ApiModelProperty(value = "分类id")
    private List<Integer> catalogIdList;
}
