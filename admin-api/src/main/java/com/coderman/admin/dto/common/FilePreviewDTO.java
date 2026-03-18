package com.coderman.admin.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：zhangyukang
 * @date ：2025/04/21 9:44
 */
@Data
public class FilePreviewDTO {
    /**
     * 阿里云前缀
     */
    @ApiModelProperty(value = "文件前缀")
    private String path;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String code;
    /**
     * 后缀
     */
    @ApiModelProperty(value = "文件后缀")
    private String suffix;
}
