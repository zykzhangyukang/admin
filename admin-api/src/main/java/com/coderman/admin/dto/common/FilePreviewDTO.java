package com.coderman.admin.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FilePreviewDTO {

    @ApiModelProperty(value = "文件前缀")
    private String path;

    @ApiModelProperty(value = "文件名")
    private String code;

    @ApiModelProperty(value = "文件后缀")
    private String suffix;
}
