package com.coderman.admin.vo.func;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2024/08/30 11:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionVO extends BaseModel {

    @ApiModelProperty(value = "菜单")
    private List<MenuVO> menus;

    @ApiModelProperty(value = "按钮key")
    private List<String> buttons;
}
