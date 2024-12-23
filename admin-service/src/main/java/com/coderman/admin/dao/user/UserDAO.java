package com.coderman.admin.dao.user;

import com.coderman.admin.model.user.UserExample;
import com.coderman.admin.model.user.UserModel;
import com.coderman.admin.vo.user.UserExcelVO;
import com.coderman.admin.vo.user.UserVO;
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
     * 查询导出列表
     * @param conditionMap
     * @return
     */
    List<UserExcelVO> selectExportList(Map<String, Object> conditionMap);


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