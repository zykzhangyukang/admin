package com.coderman.bizedu.model.lesson;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the base record class for table: edu_lesson
 * Generated by MyBatis Generator.
 * @author MyBatis Generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="LessonModel", description = "edu_lesson 实体类")
public class LessonModel extends BaseModel {
    

    @ApiModelProperty(value = "课时ID")
    private Integer lessonId;

    @ApiModelProperty(value = "所属课程id")
    private Integer courseId;

    @ApiModelProperty(value = "所属章节ID")
    private Integer chapterId;

    @ApiModelProperty(value = "课时标题")
    private String title;

    @ApiModelProperty(value = "课时内容类型")
    private String contentType;

    @ApiModelProperty(value = "课时内容URL")
    private String contentUrl;

    @ApiModelProperty(value = "课时时长（分钟）")
    private Integer durationMinutes;

    @ApiModelProperty(value = "在章节中的顺序")
    private Integer sequenceOrder;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "课时描述")
    private String description;
}