package com.coderman.admin.service.user.impl;

import com.coderman.admin.constant.AuthConstant;
import com.coderman.admin.constant.RedisConstant;
import com.coderman.admin.constant.WebSocketChannelEnum;
import com.coderman.admin.dao.role.RoleDAO;
import com.coderman.admin.dao.user.UserDAO;
import com.coderman.admin.dao.user.UserRoleDAO;
import com.coderman.admin.dto.user.*;
import com.coderman.admin.model.resc.RescModel;
import com.coderman.admin.model.role.RoleModel;
import com.coderman.admin.model.user.UserModel;
import com.coderman.admin.model.user.UserRoleExample;
import com.coderman.admin.model.user.UserRoleModel;
import com.coderman.admin.service.func.FuncService;
import com.coderman.admin.service.log.LogService;
import com.coderman.admin.service.resc.RescService;
import com.coderman.admin.service.user.UserService;
import com.coderman.admin.utils.*;
import com.coderman.admin.vo.func.MenuVO;
import com.coderman.admin.vo.func.PermissionVO;
import com.coderman.admin.vo.resc.RescVO;
import com.coderman.admin.vo.role.RoleVO;
import com.coderman.admin.vo.user.*;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.redis.service.RedisLockService;
import com.coderman.redis.service.RedisService;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import com.coderman.service.service.BaseService;
import com.coderman.sync.util.MsgBuilder;
import com.coderman.sync.util.ProjectEnum;
import com.coderman.sync.util.SyncUtil;
import com.coderman.sync.vo.PlanMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author coderman
 * @Title: 用户服务实现
 * @Description: TOD
 * @date 2022/2/2711:41
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseService implements UserService {

    @Resource
    private UserDAO userDAO;
    @Resource
    private RoleDAO roleDAO;
    @Resource
    private UserRoleDAO userRoleDAO;
    @Resource
    private RedisService redisService;
    @Resource
    private RescService rescService;
    @Resource
    private FuncService funcService;
    @Resource
    private LogService logService;
    @Resource
    private RedisLockService redisLockService;

    @Override
    @LogError(value = "切换用户登录")
    public ResultVO<TokenResultVO> switchLogin(@LogErrorParam UserSwitchLoginDTO userSwitchLoginDTO) {

        String username = userSwitchLoginDTO.getUsername();
        AuthUserVO current = AuthUtil.getCurrent();
        if (current == null) {
            return ResultUtil.getFail("请先登录后访问！");
        }

        UserVO user = this.selectUserByName(username);
        if (Objects.isNull(user)) {
            return ResultUtil.getWarn("用户不存在！");
        }

        if (AuthConstant.USER_STATUS_DISABLE.equals(user.getUserStatus())) {
            return ResultUtil.getWarn("用户已被禁用！");
        }

        AuthUserVO authUserVO = this.createSession(user.getUsername());

        // 删除当前访问令牌和刷新令牌
        this.redisService.del(AuthConstant.AUTH_ACCESS_TOKEN_NAME + current.getAccessToken(), RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.del(AuthConstant.AUTH_REFRESH_TOKEN_NAME + current.getRefreshToken(), RedisDbConstant.REDIS_DB_AUTH);

        TokenResultVO response = TokenResultVO.builder()
                .accessToken(authUserVO.getAccessToken())
                .refreshToken(authUserVO.getRefreshToken())
                .expiresIn(AuthConstant.ACCESS_TOKEN_EXPIRED_SECOND.longValue())
                .build();
        return ResultUtil.getSuccess(TokenResultVO.class, response);
    }

    @Override
    @LogError(value = "用户登录")
    public ResultVO<TokenResultVO> token(@LogErrorParam UserLoginDTO userLoginDTO) {

        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        if (StringUtils.isBlank(username)) {
            return ResultUtil.getWarn("用户名不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return ResultUtil.getWarn("登录密码不能为空！");
        }

        UserVO dbUser = this.userDAO.selectByUsernameVos(username);
        if (Objects.isNull(dbUser)) {
            return ResultUtil.getWarn("用户名或密码错误！");
        }
        if (!StringUtils.equals(PasswordUtils.encryptSHA256(password), dbUser.getPassword())) {
            return ResultUtil.getWarn("用户名或密码错误！");
        }
        if (Objects.equals(dbUser.getUserStatus(), AuthConstant.USER_STATUS_DISABLE)) {
            return ResultUtil.getWarn("用户已被禁用！");
        }

        final String lockName = "USER_LOGIN_LOCK_" + username;
        boolean lock = this.redisLockService.tryLock(lockName, TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS.toMillis(5));
        if (!lock) {
            return ResultUtil.getFail("请勿重复登录!!");
        }
        try {

            AuthUserVO authUserVO = this.createSession(dbUser.getUsername());

            // 记录日志
            this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_LEVEL_NORMAL, dbUser.getUserId(), dbUser.getUsername(), dbUser.getRealName(), "用户登录系统");

            TokenResultVO response = TokenResultVO.builder()
                    .accessToken(authUserVO.getAccessToken())
                    .refreshToken(authUserVO.getRefreshToken())
                    .expiresIn(AuthConstant.ACCESS_TOKEN_EXPIRED_SECOND.longValue())
                    .build();
            return ResultUtil.getSuccess(TokenResultVO.class, response);

        } finally {
            this.redisLockService.unlock(lockName);
        }
    }

    @Override
    @LogError(value = "获取用户信息")
    public ResultVO<UserVO> getUserInfo() {

        UserVO userVO = this.selectUserByName(AuthUtil.getUserName());
        if (userVO == null) {
            throw new BusinessException("当前用户不存在!");
        }
        if (Objects.equals(userVO.getUserStatus(), AuthConstant.USER_STATUS_DISABLE)) {
            throw new BusinessException("当前用户已被禁用!");
        }

        UserVO vo = new UserVO();
        vo.setUserId(userVO.getUserId());
        vo.setUsername(userVO.getUsername());
        vo.setRealName(userVO.getRealName());
        vo.setDeptCode(userVO.getDeptCode());
        vo.setDeptName(userVO.getDeptName());
        vo.setRoleList(userVO.getRoleList());
        vo.setCreateTime(userVO.getCreateTime());
        vo.setUpdateTime(userVO.getUpdateTime());
        vo.setUserStatus(userVO.getUserStatus());
        return ResultUtil.getSuccess(UserVO.class, userVO);
    }

    @Override
    @LogError(value = "根据token获取用户信息")
    public AuthUserVO getUserByToken(String token) {
        return this.redisService.getObject(this.getRedisKey(token), AuthUserVO.class, RedisDbConstant.REDIS_DB_AUTH);
    }

    @Override
    @LogError(value = "用户退出登录")
    public ResultVO<Void> logout(@LogErrorParam String accessToken) {

        if (StringUtils.isNotBlank(accessToken)) {
            return ResultUtil.getSuccess();
        }

        AuthUserVO authUserVO = this.redisService.getObject(AuthConstant.AUTH_ACCESS_TOKEN_NAME + accessToken, AuthUserVO.class, RedisDbConstant.REDIS_DB_AUTH);
        if (Objects.isNull(authUserVO)) {
            return ResultUtil.getSuccess();
        }

        String refreshToken = authUserVO.getRefreshToken();
        this.redisService.del(AuthConstant.AUTH_ACCESS_TOKEN_NAME + accessToken, RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.del(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshToken, RedisDbConstant.REDIS_DB_AUTH);

        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_LEVEL_NORMAL, authUserVO.getUserId(), authUserVO.getUsername(), authUserVO.getRealName(), "用户注销登录");

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "用户刷新令牌")
    public ResultVO<TokenResultVO> refreshToken(String refreshToken) {

        // 检查刷新令牌是否存在
        AuthUserVO refreshObj = this.redisService.getObject(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshToken,
                AuthUserVO.class, RedisDbConstant.REDIS_DB_AUTH);
        if (refreshObj == null) {

            return ResultUtil.getFail("用户会话已过期,请重新登录!");
        }

        // 原先的访问令牌是否过期
        AuthUserVO tokenObj = this.redisService.getObject(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshObj.getAccessToken(),
                AuthUserVO.class, RedisDbConstant.REDIS_DB_AUTH);

        TokenResultVO tokenResultVO = new TokenResultVO();
        tokenResultVO.setExpiresIn(AuthConstant.ACCESS_TOKEN_EXPIRED_SECOND.longValue());

        if (tokenObj != null) {
            tokenResultVO.setAccessToken(tokenObj.getAccessToken());
            tokenResultVO.setRefreshToken(tokenObj.getRefreshToken());
        } else {

            UserVO userVO = this.selectUserByName(refreshObj.getUsername());
            if (Objects.equals(AuthConstant.USER_STATUS_DISABLE, userVO.getUserStatus())) {
                return ResultUtil.getFail("当前用户状态已被禁用!");
            }

            AuthUserVO authUserVO = this.createSession(userVO.getUsername());
            tokenResultVO.setAccessToken(authUserVO.getAccessToken());
            tokenResultVO.setRefreshToken(authUserVO.getRefreshToken());
            // 删除之前的刷新令牌
            this.redisService.del(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshToken, RedisDbConstant.REDIS_DB_AUTH);
        }

        return ResultUtil.getSuccess(TokenResultVO.class, tokenResultVO);
    }


    /**
     * 创建用户会话信息
     *
     * @return 返回用户会话VO
     */
    private AuthUserVO createSession(String username) {

        UserVO user = this.selectUserByName(username);
        Assert.notNull(user, "用户不存在");

        // 令牌生成
        String accessToken = RandomStringUtils.randomAlphanumeric(32);
        String refreshToken = RandomStringUtils.randomAlphanumeric(32);

        // 用户资源获取
        List<RescVO> rescVOS = this.rescService.selectRescListByUsername(username);
        List<Integer> rescIdList = rescVOS.stream().map(RescModel::getRescId).distinct().collect(Collectors.toList());

        // 用户角色获取
        List<String> roleList = user.getRoleList().stream().map(RoleModel::getRoleName).distinct().collect(Collectors.toList());

        AuthUserVO authUserVO = new AuthUserVO();
        authUserVO.setAccessToken(accessToken);
        authUserVO.setRefreshToken(refreshToken);
        authUserVO.setUserId(user.getUserId());
        authUserVO.setUsername(user.getUsername());
        authUserVO.setDeptCode(user.getDeptCode());
        authUserVO.setRealName(user.getRealName());
        authUserVO.setDeptName(user.getDeptName());
        authUserVO.setRescIdList(rescIdList);
        authUserVO.setRoleList(roleList);
        authUserVO.setExpiredTime(DateUtils.addSeconds(new Date(), AuthConstant.ACCESS_TOKEN_EXPIRED_SECOND).getTime());

        // 保存会话信息到redis
        this.redisService.setObject(AuthConstant.AUTH_ACCESS_TOKEN_NAME + accessToken, authUserVO, AuthConstant.ACCESS_TOKEN_EXPIRED_SECOND, RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.setObject(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshToken, authUserVO, AuthConstant.REFRESH_TOKEN_EXPIRED_SECOND, RedisDbConstant.REDIS_DB_AUTH);
        return authUserVO;
    }

    private String getRedisKey(String userToken) {

        Assert.isTrue(StringUtils.isNotBlank(userToken), "userToken is blank");

        return AuthConstant.AUTH_ACCESS_TOKEN_NAME + userToken;
    }

    /**
     * 用户列表
     *
     * @param queryVO
     * @return
     */
    @Override
    @LogError(value = "查询用户列表")
    public ResultVO<PageVO<List<UserVO>>> page(@LogErrorParam UserPageDTO queryVO) {

        Map<String, Object> conditionMap = new HashMap<>(4);

        Integer pageSize = queryVO.getPageSize();
        Integer currentPage = queryVO.getCurrentPage();

        if (StringUtils.isNotBlank(queryVO.getUsername())) {
            conditionMap.put("username", queryVO.getUsername());
        }
        if (StringUtils.isNotBlank(queryVO.getRealName())) {
            conditionMap.put("realName", queryVO.getRealName());
        }
        if (Objects.nonNull(queryVO.getUserStatus())) {
            conditionMap.put("userStatus", queryVO.getUserStatus());
        }

        if (StringUtils.isNotBlank(queryVO.getDeptCode())) {
            conditionMap.put("deptCode", queryVO.getDeptCode());
        }
        // 字段排序
        String sortType = queryVO.getSortType();
        String sortField = queryVO.getSortField();
        if (StringUtils.isNotBlank(sortType) && StringUtils.isNotBlank(sortField)) {
            conditionMap.put("sortField", sortField);
            conditionMap.put("sortType", sortType);
        }

        PageUtil.getConditionMap(conditionMap, currentPage, pageSize);

        // 总条数
        Long count = this.userDAO.countPage(conditionMap);

        List<UserVO> userVOList = new ArrayList<>();
        if (count > 0) {
            userVOList = this.userDAO.selectPage(conditionMap);
        }
        for (UserVO userVO : userVOList) {
            List<RoleModel> roleModels = this.roleDAO.selectUserRoleList(userVO.getUserId());
            List<RoleVO> roles = roleModels.stream().map(e -> {
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(e, roleVO);
                return roleVO;
            }).collect(Collectors.toList());
            userVO.setRoleList(roles);
        }


        return ResultUtil.getSuccessPage(UserVO.class, PageUtil.getPageVO(count, userVOList, currentPage, pageSize));
    }

    /**
     * 用户创建
     *
     * @param userSaveDTO
     * @return
     */
    @Override
    @LogError(value = "新增用户信息")
    public ResultVO<Void> save(@LogErrorParam UserSaveDTO userSaveDTO) {

        AuthUserVO current = AuthUtil.getCurrent();
        String username = userSaveDTO.getUsername();
        String realName = userSaveDTO.getRealName();
        String password = userSaveDTO.getPassword();
        Integer userStatus = userSaveDTO.getUserStatus();
        String deptCode = userSaveDTO.getDeptCode();
        Date currentDate = new Date();

        Assert.notNull(current, "current is null!");

        if (StringUtils.isBlank(username)) {

            return ResultUtil.getWarn("用户账号不能为空");
        }
        if (StringUtils.length(username) < 4 || StringUtils.length(username) > 15) {

            return ResultUtil.getWarn("用户账号4-15个字符!");
        }
        if (StringUtils.isBlank(realName)) {

            return ResultUtil.getWarn("真实姓名不能为空！");
        }
        if (StringUtils.length(realName) < 2 || StringUtils.length(realName) > 10) {

            return ResultUtil.getWarn("真实姓名2-10个字符！");
        }
        if (StringUtils.isBlank(password)) {

            return ResultUtil.getWarn("登录密码不能为空！");
        }
        if (StringUtils.length(password) < 5 || StringUtils.length(password) > 15) {

            return ResultUtil.getWarn("登录密码5-15个字符！");
        }
        if (StringUtils.isBlank(deptCode)) {

            return ResultUtil.getWarn("所属部门不能为空！");
        }
        if (Objects.isNull(userStatus)) {

            return ResultUtil.getWarn("用户状态不能为空！");
        }

        // 校验是否存在账号
        UserVO userVO = this.userDAO.selectByUsernameVos(username);

        if (Objects.nonNull(userVO)) {

            return ResultUtil.getFail("存在重复的账号【" + username + "】!");
        }

        UserModel insertModel = new UserModel();
        insertModel.setUsername(username);
        insertModel.setRealName(realName);
        insertModel.setPassword(PasswordUtils.encryptSHA256(password));
        insertModel.setCreateTime(currentDate);
        insertModel.setUpdateTime(currentDate);
        insertModel.setUserStatus(userStatus);
        insertModel.setDeptCode(deptCode);

        // 新增用户
        this.userDAO.insertReturnKey(insertModel);
        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_MODULE_MIDDLE, "新增用户信息");

        PlanMsg build = MsgBuilder.create("insert_admin_sync_user", ProjectEnum.ADMIN, ProjectEnum.SYNC)
                .addIntList("insert_admin_sync_user", Collections.singletonList(insertModel.getUserId()))
                .build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @Override
    @LogError(value = "删除用户信息")
    public ResultVO<Void> delete(Integer userId) {

        AuthUserVO current = AuthUtil.getCurrent();
        if (current == null) {

            return ResultUtil.getFail("请您先登录！");
        }

        UserModel dbUserModel = this.userDAO.selectByPrimaryKey(userId);
        if (dbUserModel == null) {

            return ResultUtil.getFail("用户信息不存在！");
        }
        if (!AuthConstant.USER_STATUS_DISABLE.equals(dbUserModel.getUserStatus())) {

            return ResultUtil.getWarn("请删除禁用状态的记录！");
        }

        // 删除用户-角色关联
        this.userRoleDAO.deleteByUserId(userId);
        // 删除用户
        this.userDAO.deleteByPrimaryKey(userId);
        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_MODULE_IMPORTANT, "删除用户信息");

        PlanMsg build = MsgBuilder.create("delete_admin_sync_user", ProjectEnum.ADMIN, ProjectEnum.SYNC)
                .addIntList("delete_admin_sync_user", Collections.singletonList(userId))
                .build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "更新用户信息")
    public ResultVO<Void> update(UserUpdateDTO userUpdateDTO) {

        AuthUserVO current = AuthUtil.getCurrent();
        Assert.notNull(current, "current is null!");

        Integer userId = userUpdateDTO.getUserId();
        String realName = userUpdateDTO.getRealName();
        String deptCode = userUpdateDTO.getDeptCode();
        Integer userStatus = userUpdateDTO.getUserStatus();

        if (Objects.isNull(userId)) {
            return ResultUtil.getWarn("用户id不能为空！");
        }
        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);
        if (null == userModel) {
            return ResultUtil.getWarn("用户不存在！");
        }
        if (StringUtils.isBlank(deptCode)) {

            return ResultUtil.getWarn("所属部门不能为空！");
        }
        if (Objects.isNull(userStatus)) {

            return ResultUtil.getWarn("用户状态不能为空！");
        }
        if (StringUtils.isBlank(realName)) {

            return ResultUtil.getWarn("真实姓名不能为空！");
        }
        if (StringUtils.length(realName) < 2 || StringUtils.length(realName) > 10) {

            return ResultUtil.getWarn("真实姓名2-10个字符！");
        }

        UserModel updateModel = new UserModel();
        updateModel.setUserId(userId);
        updateModel.setUpdateTime(new Date());
        updateModel.setRealName(realName);
        updateModel.setUserStatus(userStatus);
        updateModel.setDeptCode(deptCode);

        // 更新用户
        this.userDAO.updateByPrimaryKeySelective(updateModel);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_MODULE_MIDDLE, "更新用户信息");

        PlanMsg build = MsgBuilder.create("update_admin_sync_user", ProjectEnum.ADMIN, ProjectEnum.SYNC)
                .addIntList("update_admin_sync_user", Collections.singletonList(userId))
                .build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "获取用户信息")
    public ResultVO<UserVO> selectUserById(Integer userId) {

        if (Objects.isNull(userId)) {

            return ResultUtil.getWarn("用户id不能为空！");
        }

        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);
        if (null == userModel) {

            return ResultUtil.getWarn("用户不存在！");
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        userVO.setPassword(null);
        return ResultUtil.getSuccess(UserVO.class, userVO);
    }

    @Override
    @LogError(value = "根据用户名获取用户信息")
    public UserVO selectUserByName(String username) {

        if (StringUtils.isBlank(username)) {
            throw new BusinessException("用户名参数必传！");
        }

        UserVO userVO = this.userDAO.selectByUsernameVos(username);
        if(userVO == null){
            return null;
        }

        // 查询用户角色
        List<RoleVO> roles = this.roleDAO.selectUserRoleList(userVO.getUserId()).stream()
                .map(e -> {
                    RoleVO roleVO = new RoleVO();
                    BeanUtils.copyProperties(e, roleVO);
                    return roleVO;
                })
                .collect(Collectors.toList());
        userVO.setRoleList(roles);
        return userVO;
    }

    @Override
    @LogError(value = "启用用户")
    public ResultVO<Void> updateEnable(Integer userId) {

        AuthUserVO current = AuthUtil.getCurrent();
        if (current == null) {

            return ResultUtil.getFail("请先登录后访问！");
        }

        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);
        if (Objects.isNull(userModel)) {

            return ResultUtil.getFail("用户不存在！");
        }

        if (AuthConstant.USER_STATUS_ENABLE.equals(userModel.getUserStatus())) {

            return ResultUtil.getWarn("已经是启用状态！");
        }

        UserModel updateModel = new UserModel();
        updateModel.setUserId(userId);
        updateModel.setUserStatus(AuthConstant.USER_STATUS_ENABLE);
        updateModel.setUpdateTime(new Date());
        this.userDAO.updateByPrimaryKeySelective(updateModel);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_LEVEL_NORMAL, "启用用户账号");

        PlanMsg build = MsgBuilder.createOrderlyMsg("update_admin_sync_user_status", ProjectEnum.ADMIN, ProjectEnum.SYNC, String.valueOf(userId))
                .addIntList("update_admin_sync_user_status", Collections.singletonList(userId))
                .build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "用户禁用")
    public ResultVO<Void> updateDisable(Integer userId) {

        AuthUserVO current = AuthUtil.getCurrent();
        if (current == null) {

            return ResultUtil.getFail("请先登录后访问！");
        }

        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);
        if (Objects.isNull(userModel)) {

            return ResultUtil.getFail("用户不存在！");
        }

        if (AuthConstant.USER_STATUS_DISABLE.equals(userModel.getUserStatus())) {

            return ResultUtil.getWarn("用户已经是禁用状态");
        }

        UserModel updateModel = new UserModel();
        updateModel.setUserId(userId);
        updateModel.setUserStatus(AuthConstant.USER_STATUS_DISABLE);
        updateModel.setUpdateTime(new Date());
        this.userDAO.updateByPrimaryKeySelective(updateModel);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_LEVEL_NORMAL, "禁用用户账号");

        PlanMsg build = MsgBuilder.createOrderlyMsg("update_admin_sync_user_status", ProjectEnum.ADMIN, ProjectEnum.SYNC, String.valueOf(userId))
                .addIntList("update_admin_sync_user_status", Collections.singletonList(userId))
                .build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "用户分配角色初始化")
    public ResultVO<UserRoleInitVO> selectUserRoleInit(@LogErrorParam Integer userId) {

        Assert.isTrue(Objects.nonNull(userId), "userId is null");

        UserRoleInitVO userRoleInitVO = new UserRoleInitVO();
        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);
        if (Objects.isNull(userModel)) {

            return ResultUtil.getFail("用户不存在！");
        }

        userRoleInitVO.setUserId(userId);

        // 查询全部角色信息
        List<RoleModel> roleModels = this.roleDAO.selectByExample(null);
        userRoleInitVO.setRoleList(roleModels);

        // 查询用户已有的角色
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserRoleModel> userRoleModels = this.userRoleDAO.selectByExample(example);
        List<Integer> userRoleIds = userRoleModels.stream().map(UserRoleModel::getRoleId).collect(Collectors.toList());

        userRoleInitVO.setAssignedIdList(userRoleIds);

        return ResultUtil.getSuccess(UserRoleInitVO.class, userRoleInitVO);
    }

    @Override
    @LogError(value = "用户分配角色")
    public ResultVO<Void> updateUserRole(@LogErrorParam UserRoleUpdateDTO userRoleUpdateDTO) {

        Integer userId = userRoleUpdateDTO.getUserId();
        List<Integer> roleIdList = userRoleUpdateDTO.getRoleIdList();

        if (Objects.isNull(userId)) {

            return ResultUtil.getWarn("用户id不能为空！");
        }

        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);

        if (userModel == null) {

            return ResultUtil.getWarn("用户不存在！");
        }

        // 清空之前的权限
        this.userRoleDAO.deleteByUserId(userId);

        // 批量新增
        if (!CollectionUtils.isEmpty(roleIdList)) {

            this.userRoleDAO.insertBatchByUserId(userId, roleIdList);
        }

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_MODULE_IMPORTANT, "用户分配角色");

        return ResultUtil.getSuccess();
    }


    @Override
    @LogError(value = "用户更新密码")
    public ResultVO<Void> updateUserPwd(@LogErrorParam UserPwdUpdateDTO userPwdUpdateDTO) {

        Integer userId = userPwdUpdateDTO.getUserId();
        String password = userPwdUpdateDTO.getPassword();

        if (Objects.isNull(userId)) {

            return ResultUtil.getWarn("用户id不能为空！");
        }

        if (StringUtils.isBlank(password)) {

            return ResultUtil.getWarn("登录密码不能为空！");
        }

        if (StringUtils.isBlank(password)) {

            return ResultUtil.getWarn("登录密码不能为空！");
        }

        if (StringUtils.length(password) < 5 || StringUtils.length(password) > 15) {

            return ResultUtil.getWarn("登录密码5-15个字符！");
        }

        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);

        if (userModel == null) {

            return ResultUtil.getWarn("用户不存在！");
        }

        UserModel record = new UserModel();
        record.setUserId(userId);
        record.setPassword(PasswordUtils.encryptSHA256(password));
        record.setUpdateTime(new Date());
        this.userDAO.updateByPrimaryKeySelective(record);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_MODULE_IMPORTANT, "修改用户密码");
        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "用户离线消息拉取")
    public ResultVO<List<Object>> pullNotify(Integer userId) {

        // 离线消息key
        String target = String.format(WebSocketChannelEnum.USER_SYS_MSG.getSubscribeUrl(), userId);
        String listKey = RedisConstant.REDIS_UNREAD_MSG_PREFIX + ":" + userId + ":" + target;

        // 拉取消息
        List<Object> list = this.redisService.getListData(listKey, Object.class, RedisDbConstant.REDIS_DB_DEFAULT);
        // 删除消息
        this.redisService.del(listKey, RedisDbConstant.REDIS_DB_DEFAULT);

        return ResultUtil.getSuccessList(Object.class, list);
    }

    @Override
    public ResultVO<PermissionVO> getPermission() {
        AuthUserVO currentUser = AuthUtil.getCurrent();
        Assert.notNull(currentUser, "当前用户未登录!");

        PermissionVO permissionVO = new PermissionVO();

        List<MenuVO> userMenus = this.funcService.selectUserMenus(currentUser.getUserId());
        permissionVO.setMenus(userMenus);

        List<String> userButtons = this.funcService.selectUserButtons(currentUser.getUserId());
        permissionVO.setButtons(userButtons);

        return ResultUtil.getSuccess(PermissionVO.class, permissionVO);
    }


}
