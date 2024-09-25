package com.coderman.admin.dto.common;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileChunkDTO extends BaseModel {

    @ApiModelProperty(value = "当前文件分片的索引")
    private Integer index;

    @ApiModelProperty(value = "二进制文件")
    private MultipartFile file;

    @ApiModelProperty(value = "文件hash")
    private String hash;
}
