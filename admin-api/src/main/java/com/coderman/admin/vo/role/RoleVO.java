package com.coderman.admin.vo.role;

import com.coderman.admin.model.role.RoleModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author coderman
 * @Title: 角色VO
 * @Description: TOD
 * @date 2022/2/2711:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVO extends RoleModel {

    @ApiModelProperty(value = "用户信息列表")
    private List<RoleUserVO> userVOList;
}
