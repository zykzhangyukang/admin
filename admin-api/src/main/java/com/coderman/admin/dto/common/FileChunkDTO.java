package com.coderman.admin.dto.common;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileChunkDTO extends BaseModel {

    @ApiModelProperty(value = "分片任务上传id")
    private String uploadId;

    @ApiModelProperty(value = "二进制文件")
    private MultipartFile filePart;

    @ApiModelProperty(value = "文件hash")
    private String fileHash;

    @ApiModelProperty(value = "当前分片序号")
    private Integer chunkNumber;
}
