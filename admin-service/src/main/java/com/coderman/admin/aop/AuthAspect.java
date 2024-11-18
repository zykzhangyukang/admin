package com.coderman.admin.aop;

import com.coderman.admin.constant.RedisConstant;
import com.coderman.admin.service.resc.RescService;
import com.coderman.admin.service.user.UserService;
import com.coderman.admin.utils.AuthUtil;
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

    /**
     * 保存token与用户的关系
     */
    public static final Cache<String, AuthUserVO> USER_TOKEN_CACHE_MAP = CacheBuilder.newBuilder()
            //设置缓存初始大小
            .initialCapacity(10)
            //最大值
            .maximumSize(500)
            //多线程并发数
            .concurrencyLevel(5)
            //过期时间，写入后30s过期
            .expireAfterWrite(30, TimeUnit.SECONDS)
            // 过期监听
            .removalListener((RemovalListener<String, AuthUserVO>) removalNotification -> {
                log.debug("过期会话缓存清除 token:{} is removed cause:{}", removalNotification.getKey(), removalNotification.getCause());
            })
            .recordStats()
            .build();
    /**
     * 保存缓存设备的信息
     */
    public static final Cache<Integer, String> USER_DEVICE_CACHE_MAP = CacheBuilder.newBuilder()
            //设置缓存初始大小
            .initialCapacity(10)
            //最大值
            .maximumSize(500)
            //多线程并发数
            .concurrencyLevel(5)
            //过期时间，写入后30s过期
            .expireAfterWrite(30, TimeUnit.SECONDS)
            // 过期监听
            .removalListener((RemovalListener<Integer, String>) removalNotification -> {
                log.debug("过期设备缓存清除 userId:{} is removed cause:{}", removalNotification.getKey(), removalNotification.getCause());
            })
            .recordStats()
            .build();

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

    /**
     * 清除本地缓存
     *
     * @param token
     * @param userId
     */
    private void clearCache(String token, Integer userId) {
        // 清除会话缓存
        USER_TOKEN_CACHE_MAP.invalidate(token);
        // 清除设备缓存
        USER_DEVICE_CACHE_MAP.invalidate(userId);
    }


    @Pointcut("(execution(* com.coderman..controller..*(..)))")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

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
            authUserVO = USER_TOKEN_CACHE_MAP.get(token, () -> {
                log.debug("尝试从redis中获取用户信息结果.token:{}", token);
                return userApi.getUserByToken(token);
            });
        } catch (Exception ignore) {
        }

        if (authUserVO == null || System.currentTimeMillis() > authUserVO.getExpiredTime()) {
            USER_TOKEN_CACHE_MAP.invalidate(token);
            throw new BusinessException(ResultConstant.RESULT_CODE_401, "会话已过期, 请重新登录");
        }

        // 单设备校验
        if (isOneDeviceLogin) {
            Integer userId = authUserVO.getUserId();
            String deviceToken;
            deviceToken = USER_DEVICE_CACHE_MAP.get(userId, () -> {
                log.debug("尝试从redis中获取设备信息结果.userId:{}", userId);
                return userApi.getTokenByUserId(userId);
            });
            if (StringUtils.isNotBlank(deviceToken) && !StringUtils.equals(deviceToken, token)) {
                USER_DEVICE_CACHE_MAP.invalidate(userId);
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
    public void doRefreshResc(String msgContent) {

        log.warn("doRefreshResc start - > {}", msgContent);
        this.refreshSystemAllRescMap();
        log.warn("doRefreshResc end - > {}", msgContent);
    }

    @RedisChannelListener(channelName = RedisConstant.CHANNEL_USER_LOGOUT, clazz = AuthUserVO.class)
    public void doUserLogout(AuthUserVO logoutUser) {

        String accessToken = logoutUser.getAccessToken();
        Integer userId = logoutUser.getUserId();

        log.warn("doUserLogout start - > {}", accessToken);
        this.clearCache(accessToken, userId);
        log.warn("doUserLogout end - > {}", accessToken);
    }
}
