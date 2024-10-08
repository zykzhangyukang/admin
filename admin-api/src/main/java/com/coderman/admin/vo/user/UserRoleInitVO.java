package com.coderman.admin.vo.user;

import com.coderman.admin.model.role.RoleModel;
import com.coderman.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author coderman
 * @Title: 用户分配对象
 * @Description: TOD
 * @date 2022/3/612:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleInitVO extends BaseModel {
    /**
     * 所有角色
     */
    private List<RoleModel> roleList;
    /**
     * 用户已经分配的角色id
     */
    private List<Integer> roleIdList;
}
