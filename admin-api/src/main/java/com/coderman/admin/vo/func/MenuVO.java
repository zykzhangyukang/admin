package com.coderman.admin.vo.func;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2024/08/30 11:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuVO extends BaseModel {

    @ApiModelProperty(value = "菜单id")
    private Integer id;

    @ApiModelProperty(value = "菜单key (唯一标识)")
    private String key;

    @ApiModelProperty(value = "父菜单id")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "子菜单")
    private List<MenuVO> children = new ArrayList<>();
}
