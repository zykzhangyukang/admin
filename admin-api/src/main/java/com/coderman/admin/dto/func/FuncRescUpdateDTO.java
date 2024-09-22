package com.coderman.admin.dto.func;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhangyukang
 */
@ApiModel(value = "FuncRescUpdateDTO",description = "功能分配资源DTO")
@EqualsAndHashCode(callSuper = true)
@Data
public class FuncRescUpdateDTO extends BaseModel {

    @ApiModelProperty(value = "功能id")
    private Integer funcId;

    @ApiModelProperty(value = "更新类型(add|remove)")
    private String type;

    @ApiModelProperty(value = "资源集合")
    private Integer rescId;
}


