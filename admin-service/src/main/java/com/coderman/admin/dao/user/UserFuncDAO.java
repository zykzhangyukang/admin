package com.coderman.admin.dao.user;

import com.coderman.admin.model.user.UserFuncExample;
import com.coderman.admin.model.user.UserFuncModel;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFuncDAO extends BaseDAO<UserFuncModel, UserFuncExample> {

    /**
     * 清空用户的功能
     * @param userId
     */
    void deleteByUserId(@Param(value = "userId") Integer userId);

    /**
     * 批量信息用户功能关联
     * @param userId
     * @param funcIdList
     */
    void insertBatchByUserId(@Param(value = "userId") Integer userId,@Param(value = "funcIdList") List<Integer> funcIdList);
}