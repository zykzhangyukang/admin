package com.coderman.admin.dao.role;

import com.coderman.admin.model.role.RoleFuncExample;
import com.coderman.admin.model.role.RoleFuncModel;
import com.coderman.admin.vo.user.UserVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface RoleFuncDAO extends BaseDAO<RoleFuncModel, RoleFuncExample> {


    /**
     * 批量查询角色-功能关联
     * @param roleId
     * @param funcIdList
     */
    void batchInsertByRoleId(@Param(value = "roleId") Integer roleId,@Param(value = "funcIdList") List<Integer> funcIdList);


    /**
     * 删除功能角色关联功能id集合
     * @param funcIdList
     */
    void deleteByFuncIdIn(@Param(value = "funcIdList") List<Integer> funcIdList);


    /**
     * 通过角色id删除
     * @param roleId
     */
    void deleteByRoleId(@Param(value = "roleId") Integer roleId);

    /**
     * 根据角色获取
     *
     * @param roleId
     * @return
     */
    List<RoleFuncModel> selectAllByRoleId(@Param(value = "roleId") Integer roleId);

    /**
     * 根据功能id获取用户
     *
     * @param funcId
     * @return
     */
    List<UserVO> selectUserListByFuncId(@Param(value = "funcId") Integer funcId);
}
