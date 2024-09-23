package com.coderman.admin.service.user;


import com.coderman.admin.dto.user.*;
import com.coderman.admin.vo.func.PermissionVO;
import com.coderman.admin.vo.user.AuthUserVO;
import com.coderman.admin.vo.user.TokenResultVO;
import com.coderman.admin.vo.user.UserRoleInitVO;
import com.coderman.admin.vo.user.UserVO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;

import java.util.List;

/**
 * @author coderman
 * @date 2022/2/2711:41
 */
public interface UserService {

    /**
     * 用户列表
     * @param queryVO 查询参数
     * @return
     */
    ResultVO<PageVO<List<UserVO>>> page(UserPageDTO queryVO);

    /**
     * 用户新增
     *
     * @param userSaveDTO
     * @return
     */
    ResultVO<Void> save(UserSaveDTO userSaveDTO);


    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    ResultVO<Void> delete(Integer userId);

    /**
     * 更新用户
     *
     * @param userUpdateDTO
     * @return
     */
    ResultVO<Void> update(UserUpdateDTO userUpdateDTO);


    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    ResultVO<UserVO> selectUserById(Integer userId);


    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    UserVO selectUserByName(String username);


    /**
     * 启用用户
     *
     * @param userId
     * @return
     */
    ResultVO<Void> updateEnable(Integer userId);


    /**
     * 禁用用户
     *
     * @param userId
     * @return
     */
    ResultVO<Void> updateDisable(Integer userId);


    /**
     * 用户分配角色初始化
     *
     * @param userId
     * @return
     */
    ResultVO<UserRoleInitVO> selectUserRoleInit(Integer userId);

    /**
     * 用户分配角色
     * @param userRoleUpdateDTO 参数
     * @return
     */
    ResultVO<Void> updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO);


    /**
     * 设置密码
     * @param userPwdUpdateDTO
     * @return
     */
    ResultVO<Void> updateUserPwd(UserPwdUpdateDTO userPwdUpdateDTO);


    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    ResultVO<TokenResultVO> token(UserLoginDTO userLoginDTO);


    /**
     * 用户切换登录
     *
     * @param userSwitchLoginDTO
     * @return
     */
    ResultVO<TokenResultVO> switchLogin(UserSwitchLoginDTO userSwitchLoginDTO);


    /**
     * 获取用户信息
     * @return
     */
    ResultVO<UserVO> getUserInfo();


    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return
     */
    AuthUserVO getUserByToken(String token);


    /**
     * 用户注销登录
     *
     * @param accessToken 访问令牌
     * @return
     */
    ResultVO<Void> logout(String accessToken);


    /**
     * 用户刷新登录
     * @param refreshToken 刷新令牌
     * @return
     */
    ResultVO<TokenResultVO> refreshToken(String refreshToken);

    /**
     * 获取用户权限信息
     *
     * @return
     */
    ResultVO<PermissionVO> getPermission();

}
