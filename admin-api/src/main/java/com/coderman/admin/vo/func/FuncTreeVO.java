package com.coderman.admin.vo.func;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author coderman
 * @date 2022/3/1915:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FuncTreeVO extends BaseModel {

    @ApiModelProperty(value = "功能id")
    private Integer funcId;

    @ApiModelProperty(value = "功能名称")
    private String funcName;

    @ApiModelProperty(value = "功能key")
    private String funcKey;

    @ApiModelProperty(value = "功能排序")
    private Integer funcSort;

    @ApiModelProperty(value = "父级id")
    private Integer parentId;

    @ApiModelProperty(value = "功能类型")
    private String funcType;

    @ApiModelProperty(value = "是否显示")
    private Integer hide;

    @ApiModelProperty(value = "子级功能")
    private List<FuncTreeVO> children;
}
