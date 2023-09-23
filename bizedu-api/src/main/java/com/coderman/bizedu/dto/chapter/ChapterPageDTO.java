package com.coderman.bizedu.dto.chapter;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 16:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChapterPageDTO extends BaseModel {

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "章节标题")
    private String chapterName;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;
}
