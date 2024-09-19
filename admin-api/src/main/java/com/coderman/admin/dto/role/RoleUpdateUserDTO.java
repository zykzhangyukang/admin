package com.coderman.admin.dto.role;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色更新用户
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleUpdateUserDTO extends BaseModel {

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "用户id集合")
    private List<Integer> userIdList;
}
