package com.coderman.bizedu.dao.user;

import com.coderman.bizedu.model.user.UserExample;
import com.coderman.bizedu.model.user.UserModel;
import com.coderman.bizedu.vo.user.UserVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyukang
 */
public interface UserDAO extends BaseDAO<UserModel, UserExample> {


    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    UserVO selectByUsernameVos(@Param(value = "username") String username);


    /**
     * 用户列表
     *
     * @param conditionMap 查询条件
     * @return
     */
    List<UserVO> selectPage(Map<String, Object> conditionMap);


    /**
     * 分页总条数
     *
     * @param conditionMap 查询条件
     * @return
     */
    Long countPage(Map<String, Object> conditionMap);

    /**
     *  新增用户
     *
     * @param userModel
     * @return
     */
    int insertReturnKey(UserModel userModel);
}