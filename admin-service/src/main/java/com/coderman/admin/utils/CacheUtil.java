package com.coderman.admin.utils;

import com.coderman.admin.vo.user.AuthUserVO;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author ：zhangyukang
 * @date ：2024/11/21 12:03
 */
@Slf4j
public class CacheUtil {

    /**
     * 保存token与用户的关系
     */
    private final Cache<String, AuthUserVO> USER_TOKEN_CACHE_MAP = CacheBuilder.newBuilder()
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
    private final Cache<Integer, String> USER_DEVICE_CACHE_MAP = CacheBuilder.newBuilder()
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

    public static CacheUtil getInstance() {
        return CacheUtil.Holder.INSTANCE;
    }

    private static class Holder {
        private static final CacheUtil INSTANCE = new CacheUtil();
    }

    private CacheUtil() {
    }

    public Cache<String, AuthUserVO> getTokenCache() {
        return USER_TOKEN_CACHE_MAP;
    }

    public Cache<Integer, String> getDeviceCache() {
        return USER_DEVICE_CACHE_MAP;
    }
}
