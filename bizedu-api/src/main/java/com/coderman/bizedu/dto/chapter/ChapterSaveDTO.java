package com.coderman.bizedu.dto.chapter;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 17:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChapterSaveDTO extends BaseModel {

    @ApiModelProperty(value = "课程章节id")
    private Integer chapterId;

    @ApiModelProperty(value = "章节标题")
    private String chapterName;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
