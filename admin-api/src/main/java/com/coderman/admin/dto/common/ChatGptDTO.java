package com.coderman.admin.dto.common;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatGptDTO extends BaseModel {

    @ApiModelProperty(value = "提示词")
    private String prompt;
}
