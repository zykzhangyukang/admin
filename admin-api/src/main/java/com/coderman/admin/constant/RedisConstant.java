package com.coderman.admin.constant;

/**
 *  redis发布订阅主题
 * @author zhangyukang
 */
public interface RedisConstant {

    /**
     * 刷新系统资源
     */
    public static final String CHANNEL_REFRESH_RESC = "topic://refresh_resc";
    /**
     * 用户退出登录
     */
    public static final String CHANNEL_USER_REFRESH_CACHE = "topic://refresh_user_cache";
    /**
     * websocket消息广播
     */
    public static final String CHANNEL_WEBSOCKET_NOTIFY = "topic://websocket_notify";
    /**
     * 存储websocket连接的用户
     */
    public static final String WEBSOCKET_USER_SET = "auth:websocket";
}
