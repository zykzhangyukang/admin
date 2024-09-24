package com.coderman.admin.dao.user;

import com.coderman.admin.model.user.UserRoleExample;
import com.coderman.admin.model.user.UserRoleModel;
import com.coderman.admin.vo.user.UserRoleVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface UserRoleDAO extends BaseDAO<UserRoleModel, UserRoleExample> {


    /**
     * 新增用户角色关联
     * @param userId
     * @param assignedIdList
     */
    void insertBatchByUserId(@Param(value = "userId") Integer userId, @Param(value = "roleIds") List<Integer> assignedIdList);

    /**
     * 新增角色用户关联
     * @param roleId
     * @param assignedIdList
     */
    void insertBatchByRoleId(@Param(value = "roleId") Integer roleId, @Param(value = "userIds") List<Integer> assignedIdList);

    /**
     * 根据用户id删除关联关系
     *
     * @param userId
     */
    void deleteByUserId(@Param(value = "userId") Integer userId);

    /**
     * 批量查询用户角色
     * @param userIdList
     * @return
     */
    List<UserRoleVO> selectUserRoleListBatch(@Param(value = "userIdList") List<Integer> userIdList);

}
