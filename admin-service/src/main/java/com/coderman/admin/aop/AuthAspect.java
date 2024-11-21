package com.coderman.admin.aop;

import com.coderman.admin.constant.RedisConstant;
import com.coderman.admin.service.resc.RescService;
import com.coderman.admin.service.user.UserService;
import com.coderman.admin.utils.AuthUtil;
import com.coderman.admin.utils.CacheUtil;
import com.coderman.admin.vo.user.AuthUserVO;
import com.coderman.api.constant.AopConstant;
import com.coderman.api.constant.ResultConstant;
import com.coderman.api.exception.BusinessException;
import com.coderman.redis.annotaion.RedisChannelListener;
import com.coderman.service.util.HttpContextUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 权限拦截器
 *
 * @author coderman
 * @date 2022/8/7 11:25
 */
@Aspect
@Component
@Order(value = AopConstant.AUTH_ASPECT_ORDER)
@Lazy(value = false)
@Slf4j
public class AuthAspect {

    /**
     * 白名单接口
     */
    public static List<String> whiteListUrl = new ArrayList<>();
    /**
     * 资源url与功能关系
     */
    public static Map<String, Set<Integer>> systemAllResourceMap = new HashMap<>();
    /**
     * 无需拦截的url且有登录信息
     */
    public static List<String> unFilterHasLoginInfoUrl = new ArrayList<>();
    /**
     * 资源api
     */
    @Resource
    private RescService rescApi;
    /**
     * 用户api
     */
    @Resource
    private UserService userApi;
    /**
     * 是否单设备登录校验
     */
    private static final boolean isOneDeviceLogin = true;

    @PostConstruct
    public void init() {

        // 白名单URL
        whiteListUrl.addAll(Arrays.asList(
                "/auth/user/token"
                , "/auth/user/refresh/token"
                , "/auth/user/logout"
        ));

        // 无需拦截且有会话信息URL
        unFilterHasLoginInfoUrl.addAll(Arrays.asList(
                "/auth/user/info"
                , "/auth/user/permission"
                , "/common/const/all"
                , "/common/notification/count"
                , "/common/notification/read"
                , "/common/notification/page"
        ));

        // 刷新系统资源
        refreshSystemAllRescMap();
    }

    /**
     * 刷新系统资源
     */
    public void refreshSystemAllRescMap() {
        systemAllResourceMap = this.rescApi.getSystemAllRescMap(null).getResult();
    }


    @Pointcut("(execution(* com.coderman..controller..*(..)))")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Cache<String, AuthUserVO> tokenCache = CacheUtil.getInstance().getTokenCache();
        Cache<Integer, String> deviceCache = CacheUtil.getInstance().getDeviceCache();

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String path = request.getServletPath();

        // 白名单直接放行
        if (whiteListUrl.contains(path)) {

            return point.proceed();
        }

        // 访问令牌
        String token = AuthUtil.getToken();
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(ResultConstant.RESULT_CODE_401, "会话已过期, 请重新登录");
        }
        // 系统不存在的资源直接返回
        if (!systemAllResourceMap.containsKey(path) && !unFilterHasLoginInfoUrl.contains(path)) {
            throw new BusinessException(ResultConstant.RESULT_CODE_404, "您访问的接口不存在!");
        }

        // 用户信息
        AuthUserVO authUserVO = null;
        try {
            authUserVO = tokenCache.get(token, () -> {
                log.debug("尝试从redis中获取用户信息结果.token:{}", token);
                return userApi.getUserByToken(token);
            });
        } catch (Exception ignore) {
        }

        if (authUserVO == null || System.currentTimeMillis() > authUserVO.getExpiredTime()) {
            tokenCache.invalidate(token);
            throw new BusinessException(ResultConstant.RESULT_CODE_401, "会话已过期, 请重新登录");
        }

        // 单设备校验
        if (isOneDeviceLogin) {
            Integer userId = authUserVO.getUserId();
            String deviceToken = StringUtils.EMPTY;
            try {
                deviceToken = deviceCache.get(userId, () -> {
                    log.debug("尝试从redis中获取设备信息结果.userId:{}", userId);
                    return userApi.getTokenByUserId(userId);
                });
            } catch (Exception ignore) {
            }
            if (StringUtils.isNotBlank(deviceToken) && !StringUtils.equals(deviceToken, token)) {
                deviceCache.invalidate(userId);
                throw new BusinessException(ResultConstant.RESULT_CODE_401, "账号已在其他设备上登录！");
            }
        }

        // 不需要过滤的url且有登入信息,设置会话后直接放行
        if (unFilterHasLoginInfoUrl.contains(path)) {
            AuthUtil.setCurrent(authUserVO);
            return point.proceed();
        }

        // 验证用户权限
        List<Integer> myRescIds = authUserVO.getRescIdList();
        Set<Integer> rescIds = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(systemAllResourceMap.get(path))) {
            rescIds = new HashSet<>(systemAllResourceMap.get(path));
        }

        if (CollectionUtils.isNotEmpty(myRescIds)) {
            for (Integer rescId : rescIds) {
                if (myRescIds.contains(rescId)) {
                    AuthUtil.setCurrent(authUserVO);
                    return point.proceed();
                }
            }

        }

        throw new BusinessException(ResultConstant.RESULT_CODE_403, "接口无权限");
    }

    @RedisChannelListener(channelName = RedisConstant.CHANNEL_REFRESH_RESC)
    public void refreshRescListener(String msgContent) {

        log.warn("doRefreshResc start - > {}", msgContent);
        this.refreshSystemAllRescMap();
        log.warn("doRefreshResc end - > {}", msgContent);
    }

    @RedisChannelListener(channelName = RedisConstant.CHANNEL_USER_REFRESH_CACHE, clazz = AuthUserVO.class)
    public void logoutListener(AuthUserVO logoutUser) {

        String token = logoutUser.getAccessToken();
        Integer userId = logoutUser.getUserId();

        log.warn("doUserLogout start - > {}", token);

        // 清除会话缓存
        Cache<String, AuthUserVO> tokenCache = CacheUtil.getInstance().getTokenCache();
        tokenCache.invalidate(token);

        // 清除设备缓存
        Cache<Integer, String> deviceCache = CacheUtil.getInstance().getDeviceCache();
        deviceCache.invalidate(userId);

        log.warn("doUserLogout end - > {}", token);
    }
}
