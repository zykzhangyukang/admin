package com.coderman.admin.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.admin.constant.AuthConstant;
import com.coderman.admin.constant.FileConstant;
import com.coderman.admin.constant.NotificationConstant;
import com.coderman.admin.constant.RedisConstant;
import com.coderman.admin.dao.role.RoleDAO;
import com.coderman.admin.dao.user.UserDAO;
import com.coderman.admin.dao.user.UserFuncDAO;
import com.coderman.admin.dao.user.UserRoleDAO;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.dto.user.*;
import com.coderman.admin.model.resc.RescModel;
import com.coderman.admin.model.role.RoleModel;
import com.coderman.admin.model.user.UserModel;
import com.coderman.admin.service.common.NotificationService;
import com.coderman.admin.service.func.FuncService;
import com.coderman.admin.service.log.LogService;
import com.coderman.admin.service.resc.RescService;
import com.coderman.admin.service.user.UserService;
import com.coderman.admin.utils.*;
import com.coderman.admin.vo.func.MenuVO;
import com.coderman.admin.vo.resc.RescVO;
import com.coderman.admin.vo.user.*;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.constant.ResultConstant;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.oss.enums.FileModuleEnum;
import com.coderman.oss.util.AliYunOssUtil;
import com.coderman.redis.service.RedisLockService;
import com.coderman.redis.service.RedisService;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import com.coderman.service.service.BaseService;
import com.coderman.service.util.IpUtil;
import com.coderman.sync.util.MsgBuilder;
import com.coderman.sync.util.ProjectEnum;
import com.coderman.sync.util.SyncUtil;
import com.coderman.sync.vo.PlanMsg;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private UserFuncDAO userFuncDAO;
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
    @Resource
    private NotificationService notificationService;

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

        // 删除当前访问令牌和刷新令牌
        this.redisService.del(AuthConstant.AUTH_ACCESS_TOKEN_NAME + current.getAccessToken(), RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.del(AuthConstant.AUTH_REFRESH_TOKEN_NAME + current.getRefreshToken(), RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.del(AuthConstant.AUTH_DEVICE_TOKEN_NAME + current.getUserId(), RedisDbConstant.REDIS_DB_AUTH);

        AuthUserVO authUserVO = this.createSession(user.getUsername());

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_LEVEL_NORMAL, authUserVO.getUserId(), authUserVO.getUsername(), authUserVO.getRealName(), "切换用户登录");

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

        final String lockName = "USER_TOKEN_LOCK_" + username;
        boolean lock = this.redisLockService.tryLock(lockName, TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS.toMillis(5));
        if (!lock) {
            return ResultUtil.getFail("请勿重复登录!!");
        }

        try {
            // 是否有其他设备登录， 如果有则需要踢出该设备
            String oldSession = this.redisService.getString(AuthConstant.AUTH_DEVICE_TOKEN_NAME + dbUser.getUserId(), RedisDbConstant.REDIS_DB_AUTH);
            // 创建用户会话
            AuthUserVO authUserVO = this.createSession(dbUser.getUsername());
            // 记录登录日志
            this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_LEVEL_NORMAL, dbUser.getUserId(), dbUser.getUsername(), dbUser.getRealName(), "用户登录系统");
            // 多设备登录提醒
            this.sendMultiDeviceLoginNotification(authUserVO, oldSession);

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

    /**
     * 通知其他设备下线
     *
     * @param authUserVO 用户会话
     * @param oldSession 旧session(token)
     */
    private void sendMultiDeviceLoginNotification(AuthUserVO authUserVO, String oldSession) {
        if (StringUtils.isNotBlank(oldSession) && !StringUtils.equals(oldSession, authUserVO.getAccessToken())) {
            String message = String.format("系统检测到当前账号于%s在其他设备上登录-%s(%s)。若非本人操作，您的登录密码可能已经泄露，" +
                            "请及时更改密码，紧急情况可联系管理员冻结账号。（管理员邮箱：3053161401@qq.com）",
                    DateFormatUtils.format(new Date(), "HH:mm:ss"),
                    IpUtil.getCityInfo(),
                    IpUtil.getIpAddr()
            );

            NotificationDTO msg = NotificationDTO.builder()
                    .userId(authUserVO.getUserId())
                    .title("设备登录系统")
                    .sessionKey(oldSession)
                    .message(message)
                    .type(NotificationConstant.NOTIFICATION_DEVICE_CHECK)
                    .build();

            notificationService.sendToUserSession(msg);
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
        vo.setDeptId(userVO.getDeptId());
        vo.setDeptName(userVO.getDeptName());
        vo.setRoleList(userVO.getRoleList());
        vo.setCreateTime(userVO.getCreateTime());
        vo.setUpdateTime(userVO.getUpdateTime());
        vo.setUserStatus(userVO.getUserStatus());
        vo.setPassword(null);
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

        if (StringUtils.isBlank(accessToken)) {
            return ResultUtil.getSuccess();
        }

        AuthUserVO authUserVO = this.redisService.getObject(AuthConstant.AUTH_ACCESS_TOKEN_NAME + accessToken, AuthUserVO.class, RedisDbConstant.REDIS_DB_AUTH);
        if (Objects.isNull(authUserVO)) {
            return ResultUtil.getSuccess();
        }

        String refreshToken = authUserVO.getRefreshToken();
        this.redisService.del(AuthConstant.AUTH_ACCESS_TOKEN_NAME + accessToken, RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.del(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshToken, RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.del(AuthConstant.AUTH_DEVICE_TOKEN_NAME + authUserVO.getUserId(), RedisDbConstant.REDIS_DB_AUTH);

        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_LEVEL_NORMAL, authUserVO.getUserId(), authUserVO.getUsername(), authUserVO.getRealName(), "用户注销登录");

        // 发送广播
        this.redisService.sendTopicMessage(RedisConstant.CHANNEL_REFRESH_SESSION_CACHE, authUserVO);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "用户刷新令牌")
    public ResultVO<TokenResultVO> refreshToken(String refreshToken) {

        final String lockName = "USER_REFRESH_TOKEN_LOCK_" + refreshToken;
        boolean lock = this.redisLockService.tryLock(lockName, TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS.toMillis(5));
        if (!lock) {
            return ResultUtil.getFail("请勿重复登录!");
        }

        try {
            // 检查刷新令牌是否存在
            AuthUserVO refreshObj = this.redisService.getObject(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshToken,
                    AuthUserVO.class, RedisDbConstant.REDIS_DB_AUTH);
            if (refreshObj == null) {
                throw new BusinessException(ResultConstant.RESULT_CODE_401, "用户会话已过期,请重新登录!");
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
                    throw new BusinessException(ResultConstant.RESULT_CODE_401, "当前用户状态已被禁用!");
                }

                AuthUserVO authUserVO = this.createSession(userVO.getUsername());
                tokenResultVO.setAccessToken(authUserVO.getAccessToken());
                tokenResultVO.setRefreshToken(authUserVO.getRefreshToken());
                // 删除之前的刷新令牌
                this.redisService.del(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshToken, RedisDbConstant.REDIS_DB_AUTH);
            }

            log.warn("用户刷新令牌，refreshToken:{}, tokenResultVO:{}", refreshToken, JSON.toJSONString(tokenResultVO));

            return ResultUtil.getSuccess(TokenResultVO.class, tokenResultVO);
        } finally {

            this.redisLockService.unlock(lockName);
        }
    }


    /**
     * 创建用户会话信息
     *
     * @param username 用户名
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
        List<String> roleList = user.getRoleList().stream().map(UserRoleVO::getRoleName).distinct().collect(Collectors.toList());

        AuthUserVO authUserVO = new AuthUserVO();
        authUserVO.setAccessToken(accessToken);
        authUserVO.setRefreshToken(refreshToken);
        authUserVO.setUserId(user.getUserId());
        authUserVO.setUsername(user.getUsername());
        authUserVO.setDeptId(user.getDeptId());
        authUserVO.setRealName(user.getRealName());
        authUserVO.setDeptName(user.getDeptName());
        authUserVO.setRescIdList(rescIdList);
        authUserVO.setRoleList(roleList);
        authUserVO.setAvatar(user.getAvatar());
        authUserVO.setExpiredTime(DateUtils.addSeconds(new Date(), AuthConstant.ACCESS_TOKEN_EXPIRED_SECOND).getTime());

        // 保存会话信息到redis
        this.redisService.setObject(AuthConstant.AUTH_ACCESS_TOKEN_NAME + accessToken, authUserVO, AuthConstant.ACCESS_TOKEN_EXPIRED_SECOND, RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.setObject(AuthConstant.AUTH_REFRESH_TOKEN_NAME + refreshToken, authUserVO, AuthConstant.REFRESH_TOKEN_EXPIRED_SECOND, RedisDbConstant.REDIS_DB_AUTH);
        this.redisService.setString(AuthConstant.AUTH_DEVICE_TOKEN_NAME + authUserVO.getUserId(), accessToken, AuthConstant.ACCESS_TOKEN_EXPIRED_SECOND, RedisDbConstant.REDIS_DB_AUTH);
        // 缓存广播
        this.redisService.sendTopicMessage(RedisConstant.CHANNEL_REFRESH_SESSION_CACHE, authUserVO);

        return authUserVO;
    }

    private String getRedisKey(String userToken) {

        Assert.isTrue(StringUtils.isNotBlank(userToken), "userToken is blank");

        return AuthConstant.AUTH_ACCESS_TOKEN_NAME + userToken;
    }

    /**
     * 用户列表
     *
     * @param dto
     * @return
     */
    @Override
    @LogError(value = "查询用户列表")
    public ResultVO<PageVO<List<UserVO>>> page(@LogErrorParam UserPageDTO dto) {

        Integer pageSize = dto.getPageSize();
        Integer currentPage = dto.getCurrentPage();

        // 查询条件
        Map<String, Object> conditionMap = getCondition(dto);
        // 分页
        PageUtil.getConditionMap(conditionMap, currentPage, pageSize);

        // 总条数
        Long count = this.userDAO.countPage(conditionMap);
        List<UserVO> list = new ArrayList<>();
        if (count > 0) {
            list = this.userDAO.selectPage(conditionMap);
            for (UserVO userVO : list) {
                userVO.setPhone(MaskUtil.maskPhone(userVO.getPhone()));
            }
        }
        return ResultUtil.getSuccessPage(UserVO.class, PageUtil.getPageVO(count, list, currentPage, pageSize));
    }

    private Map<String, Object> getCondition(@LogErrorParam UserPageDTO queryVO) {
        Map<String, Object> conditionMap = new HashMap<>(4);

        if (StringUtils.isNotBlank(queryVO.getUsername())) {
            conditionMap.put("username", queryVO.getUsername());
        }
        if (StringUtils.isNotBlank(queryVO.getPhone())) {
            conditionMap.put("phone", queryVO.getPhone());
        }
        if (StringUtils.isNotBlank(queryVO.getEmail())) {
            conditionMap.put("email", queryVO.getEmail());
        }
        if (Objects.nonNull(queryVO.getDeptId())) {
            conditionMap.put("deptId", queryVO.getDeptId());
        }
        if (StringUtils.isNotBlank(queryVO.getRealName())) {
            conditionMap.put("realName", queryVO.getRealName());
        }
        if (Objects.nonNull(queryVO.getUserStatus())) {
            conditionMap.put("userStatus", queryVO.getUserStatus());
        }
        if (Objects.nonNull(queryVO.getStartTime())) {
            conditionMap.put("startTime", queryVO.getStartTime());
        }
        if (Objects.nonNull(queryVO.getEndTime())) {
            conditionMap.put("endTime", queryVO.getEndTime());
        }
        // 字段排序
        String sortType = queryVO.getSortType();
        String sortField = queryVO.getSortField();
        if (StringUtils.isNotBlank(sortType) && StringUtils.isNotBlank(sortField)) {
            conditionMap.put("sortField", sortField);
            conditionMap.put("sortType", sortType);
        }
        return conditionMap;
    }

    /**
     * 用户创建
     *
     * @param dto
     * @return
     */
    @Override
    @LogError(value = "新增用户信息")
    public ResultVO<Void> save(@LogErrorParam UserSaveDTO dto) {

        Date currentDate = new Date();

        String username = dto.getUsername();
        String realName = dto.getRealName();
        String password = dto.getPassword();
        Integer userStatus = dto.getUserStatus();
        Integer deptId = dto.getDeptId();
        String phone = dto.getPhone();
        String email = dto.getEmail();

        if (!ValidationUtil.isValidUsername(username)) {
            return ResultUtil.getWarn("用户账号必须是 3 到 20 个字符，以字母开头，可以包含字母、数字、下划线和连字符！");
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
        if (Objects.isNull(deptId)) {
            return ResultUtil.getWarn("所属部门不能为空！");
        }
        if (Objects.isNull(userStatus)) {
            return ResultUtil.getWarn("用户状态不能为空！");
        }
        if (!ValidationUtil.isValidPhone(phone)) {
            return ResultUtil.getWarn("手机号填写不合法");
        }
        if (!ValidationUtil.isValidEmail(email)) {
            return ResultUtil.getWarn("邮箱填写不合法");
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
        insertModel.setDeptId(deptId);
        insertModel.setEmail(email);
        insertModel.setPhone(phone);
        insertModel.setAvatar(this.createAvatar(insertModel.getUsername()));

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
     * 生成用户头像
     *
     * @param username
     * @return
     */
    private String createAvatar(String username) {
        String filePath = AliYunOssUtil.getInstance().genFilePath(username.hashCode() + "_.png", FileModuleEnum.USER_MODULE);
        try {
            byte[] bytes = AvatarUtil.create(username.hashCode());
            AliYunOssUtil.getInstance().uploadStream(new ByteArrayInputStream(bytes), filePath);
        } catch (Exception e) {
            log.error("生成用户头像失败:{}, 上传失败设置默认头像: {}", e.getMessage(), FileConstant.DEFAULT_AVATAR, e);
            return FileConstant.DEFAULT_AVATAR;
        }
        return FileConstant.OSS_FILE_DOMAIN + filePath;
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
        Integer deptId = userUpdateDTO.getDeptId();
        Integer userStatus = userUpdateDTO.getUserStatus();
        String phone = userUpdateDTO.getPhone();
        String email = userUpdateDTO.getEmail();
        String avatar = userUpdateDTO.getAvatar();

        if (Objects.isNull(userId)) {
            return ResultUtil.getWarn("用户id不能为空！");
        }
        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);
        if (null == userModel) {
            return ResultUtil.getWarn("用户不存在！");
        }
        if (Objects.isNull(deptId)) {

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
        if (!ValidationUtil.isValidPhone(phone)) {
            return ResultUtil.getWarn("手机号填写不合法！");
        }
        if (!ValidationUtil.isValidEmail(email)) {
            return ResultUtil.getWarn("邮箱填写不合法！");
        }
        if (StringUtils.isBlank(avatar)) {
            avatar = FileConstant.DEFAULT_AVATAR;
        }

        UserModel updateModel = new UserModel();
        updateModel.setUserId(userId);
        updateModel.setUpdateTime(new Date());
        updateModel.setRealName(realName);
        updateModel.setUserStatus(userStatus);
        updateModel.setDeptId(deptId);
        updateModel.setEmail(email);
        updateModel.setPhone(phone);
        updateModel.setAvatar(avatar);

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
        if (userVO == null) {
            return null;
        }

        // 查询用户角色
        List<UserRoleVO> roleList = this.userRoleDAO.selectRoleListByUserIds(Collections.singletonList(userVO.getUserId()));
        userVO.setRoleList(roleList);
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

        // 查询全部角色信息
        List<RoleModel> roleModels = this.roleDAO.selectByExample(null);
        userRoleInitVO.setRoleList(roleModels);

        // 查询用户已有的角色
        List<UserRoleVO> roleList = this.userRoleDAO.selectRoleListByUserIds(Collections.singletonList(userId));
        List<Integer> roleIds = roleList.stream().map(UserRoleVO::getRoleId).collect(Collectors.toList());
        userRoleInitVO.setRoleIdList(roleIds);

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

        // 清空之前的角色
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
    @LogError(value = "用户分配功能")
    public ResultVO<Void> updateUserFunc(UserFuncUpdateDTO dto) {

        Integer userId = dto.getUserId();
        Assert.notNull(userId, "用户id不能为空");

        // 清空之前的功能
        this.userFuncDAO.deleteByUserId(userId);
        // 批量新增
        List<Integer> funcIdList = dto.getFuncIdList();
        if (!CollectionUtils.isEmpty(funcIdList)) {
            this.userFuncDAO.insertBatchByUserId(userId, funcIdList);
        }

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_USER, AuthConstant.LOG_MODULE_IMPORTANT, "用户分配功能");

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "用户列表导出")
    public void export(UserPageDTO dto) {
        // 查询条件
        Map<String, Object> conditionMap = this.getCondition(dto);
        // 查询数据
        List<UserExcelVO> list = this.userDAO.selectExportList(conditionMap);
        // 导出excel
        EasyExcelUtils.exportExcel(UserExcelVO.class, list, "用户列表.xlsx");
    }

    @Override
    @LogError(value = "查看用户手机")
    public ResultVO<String> selectUserPhone(Integer userId) {

        UserModel userModel = this.userDAO.selectByPrimaryKey(userId);
        if (userModel == null) {
            return ResultUtil.getFail("用户不存在!");
        }

        return ResultUtil.getSuccess(String.class, userModel.getPhone());
    }

    @SneakyThrows
    @Override
    @LogError(value = "上传用户头像")
    public ResultVO<String> uploadAvatar(MultipartFile file) {


        final String[] FILE_SUFFIX_SUPPORT = {".jpg", ".jpeg", ".png", ".webp"};

        if (file == null || file.isEmpty()) {
            return ResultUtil.getWarn("上传的头像不能为空！");
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            return ResultUtil.getWarn("上传的头像不能大于2M！");
        }

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));

        if (!Arrays.asList(FILE_SUFFIX_SUPPORT).contains(suffix.toLowerCase(Locale.ROOT))) {

            return ResultUtil.getWarn("文件格式不支持,请更换后重试!");
        }

        String path = AliYunOssUtil.getInstance().genFilePath(originalFilename, FileModuleEnum.USER_MODULE);
        AliYunOssUtil.getInstance().uploadStreamIfNotExist(file.getInputStream(), path);


        return ResultUtil.getSuccess(String.class, FileConstant.OSS_FILE_DOMAIN + path);
    }

    @Override
    @LogError(value = "根据用户id获取token")
    public String getTokenByUserId(Integer userId) {
        if (userId == null) {
            return StringUtils.EMPTY;
        }
        return this.redisService.getString(AuthConstant.AUTH_DEVICE_TOKEN_NAME + userId, RedisDbConstant.REDIS_DB_AUTH);
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
    @LogError(value = "获取用户权限信息")
    public ResultVO<Map<String, Object>> getPermissionInfo() {
        AuthUserVO currentUser = AuthUtil.getCurrent();
        Assert.notNull(currentUser, "当前用户未登录!");

        List<MenuVO> userAllMenus = this.funcService.selectUserAllMenus(currentUser.getUserId());

        // 菜单权限树
        List<MenuVO> userMenus = this.funcService.selectUserMenusTree(userAllMenus);

        // 按钮权限
        List<String> userButtons = this.funcService.selectUserButtons(currentUser.getUserId());
        List<String> menusKeys = userAllMenus.stream().map(MenuVO::getKey).distinct().collect(Collectors.toList());
        List<String> permissions = Stream.of(menusKeys, userButtons).flatMap(Collection::stream).distinct().collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("menus", userMenus);
        map.put("permissions", permissions);
        return ResultUtil.getSuccessMap(Map.class, map);
    }


}
