package com.coderman.admin.vo.course;

import com.coderman.admin.model.course.CourseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseVO extends CourseModel {

    @ApiModelProperty(value = "课程创建人")
    private String creatorName;

}
