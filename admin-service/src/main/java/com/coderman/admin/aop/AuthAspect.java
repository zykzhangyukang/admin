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
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 权限拦截器
 *
 * @author coderman
 * @date 2022/8/7 11:25
 */
@Aspect
@Component
@Order(AopConstant.AUTH_ASPECT_ORDER)
@Lazy(false)
@Slf4j
public class AuthAspect {

    private static final boolean IS_ONE_DEVICE_LOGIN = true;

    @Resource
    private RescService rescApi;

    @Resource
    private UserService userApi;

    private static final Set<String> WHITE_LIST_URLS = Sets.newHashSet(
            "/auth/user/token",
            "/auth/user/refresh/token",
            "/auth/user/logout"
    );

    private static final Set<String> UNFILTER_HAS_LOGIN_INFO_URLS = Sets.newHashSet(
            "/auth/user/info",
            "/auth/user/permission",
            "/common/const/all",
            "/common/notification/count",
            "/common/notification/read",
            "/common/notification/page",
            "/common/chat/completion"
    );

    private static volatile Map<String, Set<Integer>> systemAllResourceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        refreshSystemAllRescMap();
    }

    public void refreshSystemAllRescMap() {
        systemAllResourceMap = rescApi.getSystemAllRescMap(null).getResult();
        log.info("System resource map refreshed successfully.");
    }

    @Pointcut("execution(* com.coderman..controller..*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String path = request.getServletPath();

        if (isWhiteListed(path)) {
            return point.proceed();
        }

        String token = AuthUtil.getToken();
        if (StringUtils.isBlank(token)) {
            throwUnauthorized("会话已过期, 请重新登录");
        }

        AuthUserVO authUserVO = validateToken(token);
        Assert.notNull(authUserVO , "会话已过期, 请重新登录");

        if (IS_ONE_DEVICE_LOGIN) {
            validateDevice(authUserVO, token);
        }

        if (UNFILTER_HAS_LOGIN_INFO_URLS.contains(path)) {
            AuthUtil.setCurrent(authUserVO);
            return point.proceed();
        }

        validatePermission(authUserVO, path);
        AuthUtil.setCurrent(authUserVO);
        return point.proceed();
    }

    private boolean isWhiteListed(String path) {
        return WHITE_LIST_URLS.contains(path);
    }

    private AuthUserVO validateToken(String token) {
        Cache<String, AuthUserVO> tokenCache = CacheUtil.getInstance().getTokenCache();
        AuthUserVO authUserVO;

        try {
            authUserVO = tokenCache.get(token, () -> userApi.getUserByToken(token));
        } catch (Exception ignore) {
            log.error("Error retrieving user from cache: token={}", token);
            throwUnauthorized("会话已过期, 请重新登录");
            return null; // Unreachable
        }

        if (authUserVO == null || System.currentTimeMillis() > authUserVO.getExpiredTime()) {
            tokenCache.invalidate(token);
            throwUnauthorized("会话已过期, 请重新登录");
        }

        return authUserVO;
    }

    private void validateDevice(AuthUserVO authUserVO, String token) {
        Cache<Integer, String> deviceCache = CacheUtil.getInstance().getDeviceCache();
        Integer userId = authUserVO.getUserId();

        try {
            String storedToken = deviceCache.get(userId, () -> userApi.getTokenByUserId(userId));
            if (!StringUtils.equals(storedToken, token)) {
                deviceCache.invalidate(userId);
                throwUnauthorized("账号已在其他设备上登录！");
            }
        } catch (Exception e) {
            log.error("Error validating device token: userId={}", userId);
            throwUnauthorized(e.getMessage());
        }
    }

    private void validatePermission(AuthUserVO authUserVO, String path) {
        List<Integer> userRescIds = authUserVO.getRescIdList();
        Set<Integer> pathRescIds = systemAllResourceMap.getOrDefault(path, Collections.emptySet());

        if (CollectionUtils.isEmpty(userRescIds) || !CollectionUtils.containsAny(userRescIds, pathRescIds)) {
            throwForbidden();
        }
    }

    private void throwUnauthorized(String message) {
        throw new BusinessException(ResultConstant.RESULT_CODE_401, message);
    }

    private void throwForbidden() {
        throw new BusinessException(ResultConstant.RESULT_CODE_403, "接口无权限");
    }

    @RedisChannelListener(channelName = RedisConstant.CHANNEL_REFRESH_RESC)
    public void refreshRescListener(String msgContent) {
        log.warn("Refreshing system resources: {}", msgContent);
        refreshSystemAllRescMap();
    }

    @RedisChannelListener(channelName = RedisConstant.CHANNEL_REFRESH_SESSION_CACHE, clazz = AuthUserVO.class)
    public void refreshSessionCache(AuthUserVO logoutUser) {
        log.warn("Clearing session cache: {}", logoutUser);

        Cache<String, AuthUserVO> tokenCache = CacheUtil.getInstance().getTokenCache();
        tokenCache.invalidate(logoutUser.getAccessToken());

        Cache<Integer, String> deviceCache = CacheUtil.getInstance().getDeviceCache();
        deviceCache.invalidate(logoutUser.getUserId());

        log.info("Session cache cleared successfully for user: {}", logoutUser.getUserId());
    }
}
