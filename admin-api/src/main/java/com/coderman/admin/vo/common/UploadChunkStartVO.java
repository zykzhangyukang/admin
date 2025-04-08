package com.coderman.admin.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author ：zhangyukang
 * @date ：2025/04/08 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadChunkStartVO {

    @ApiModelProperty(value = "阿里云oss任务id")
    private String uploadId;

    @ApiModelProperty(value = "已经上传的分块")
    private Set<Integer> uploaded;
}
