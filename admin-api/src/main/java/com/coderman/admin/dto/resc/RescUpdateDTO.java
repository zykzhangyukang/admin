package com.coderman.admin.dto.resc;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RescUpdateDTO extends BaseModel {

    @ApiModelProperty("资源id")
    private Integer rescId;

    @ApiModelProperty("资源名称")
    private String rescName;

    @ApiModelProperty("资源url")
    private String rescUrl;

    @ApiModelProperty("资源所属系统")
    private String rescDomain;

    @ApiModelProperty("请求方式")
    private String methodType;

}
