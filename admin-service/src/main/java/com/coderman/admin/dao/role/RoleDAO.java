package com.coderman.admin.dao.role;

import com.coderman.admin.model.role.RoleExample;
import com.coderman.admin.model.role.RoleModel;
import com.coderman.admin.vo.role.RoleVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyukang
 */
public interface RoleDAO extends BaseDAO<RoleModel, RoleExample> {


    /**
     * 角色列表
     * @param conditionMap
     * @return
     */
    List<RoleVO> page(Map<String,Object> conditionMap);

    /**
     * 分页条数
     *
     * @param conditionMap
     * @return
     */
    Long countPage(Map<String, Object> conditionMap);


    /**
     * 根据角色名查询角色
     *
     * @param roleName 角色名称
     * @return
     */
    RoleModel selectByRoleName(@Param(value = "roleName") String roleName);

    /**
     * 新增并返回主键
     *
     * @param roleModel 角色
     * @return
     */
    int insertReturnKey(RoleModel roleModel);
}
