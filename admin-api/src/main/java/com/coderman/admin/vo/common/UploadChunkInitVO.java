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
public class UploadChunkInitVO {

    @ApiModelProperty(value = "阿里云oss任务id")
    private String uploadId;

    @ApiModelProperty(value = "已经上传的分块")
    private Set<Integer> uploaded;

    @ApiModelProperty(value = "是否秒传")
    private Boolean isSkip;

    @ApiModelProperty(value = "秒传文件地址")
    private String filePath;
}
