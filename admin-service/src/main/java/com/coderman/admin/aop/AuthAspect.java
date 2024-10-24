package com.coderman.admin.aop;

import com.coderman.admin.constant.RedisConstant;
import com.coderman.admin.service.resc.RescService;
import com.coderman.admin.service.user.UserService;
import com.coderman.admin.utils.AuthUtil;
import com.coderman.admin.vo.user.AuthUserVO;
import com.coderman.api.constant.AopConstant;
import com.coderman.api.constant.ResultConstant;
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
import javax.servlet.http.HttpServletResponse;
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
     * 保存token与用户的关系
     */
    public static final Cache<String, AuthUserVO> USER_TOKEN_CACHE_MAP = CacheBuilder.newBuilder()
            //设置缓存初始大小
            .initialCapacity(10)
            //最大值
            .maximumSize(500)
            //多线程并发数
            .concurrencyLevel(5)
            //过期时间，写入后5分钟过期
            .expireAfterWrite(5, TimeUnit.MINUTES)
            // 过期监听
            .removalListener((RemovalListener<String, AuthUserVO>) removalNotification -> {
                log.debug("过期会话缓存清除 token:{} is removed cause:{}", removalNotification.getKey(), removalNotification.getCause());
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
                , "/common/fund/list"
                , "/common/fund/history"
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

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
        String path = request.getServletPath();

        // 白名单直接放行
        if (whiteListUrl.contains(path)) {

            return point.proceed();
        }

        // 访问令牌
        String token = AuthUtil.getAccessToken();
        if (StringUtils.isBlank(token)) {
            response.setStatus(ResultConstant.RESULT_CODE_401);
            return null;
        }

        // 系统不存在的资源直接返回
        if (!systemAllResourceMap.containsKey(path) && !unFilterHasLoginInfoUrl.contains(path)) {
            response.setStatus(ResultConstant.RESULT_CODE_404);
            return null;
        }

        // 用户信息
        AuthUserVO authUserVO = null;
        try {
            authUserVO = USER_TOKEN_CACHE_MAP.get(token, () -> {

                log.debug("尝试从redis中获取用户信息结果.token:{}", token);
                return userApi.getUserByToken(token);
            });
        } catch (Exception e) {

            log.error("尝试从redis中获取用户信息结果失败:{}", e.getMessage());
        }


        if (authUserVO == null || System.currentTimeMillis() > authUserVO.getExpiredTime()) {

            USER_TOKEN_CACHE_MAP.invalidate(token);
            assert response != null;
            response.setStatus(ResultConstant.RESULT_CODE_401);
            return null;
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


        assert response != null;
        response.setStatus(ResultConstant.RESULT_CODE_403);
        return null;
    }

    @RedisChannelListener(channelName = RedisConstant.CHANNEL_REFRESH_RESC)
    public void doRefresh(String msgContent) {

        log.warn("doRefresh start - > {}", msgContent);

        // 刷新系统资源
        this.refreshSystemAllRescMap();

        log.warn("doRefresh end - > {}", msgContent);
    }
}
