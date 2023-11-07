package com.coderman.bizedu.constant;

/**
 *  redis发布订阅主题
 * @author zhangyukang
 */
public interface RedisConstant {

    /**
     * 刷新同步计划
     */
    public static final String TOPIC_REFRESH_PLAN = "TOPIC://REFRESH_PLAN";

    /**
     * 刷新系统资源
     */
    public static final String CHANNEL_REFRESH_RESC = "TOPIC://REFRESH_RESC";

    /**
     * websocket消息广告
     */
    public static final String CHANNEL_WEBSOCKET_NOTIFY = "TOPIC://WEBSOCKET_NOTIFY";

    /**
     * 离线消息通知
     */
    public static final String REDIS_UNREAD_MSG_PREFIX = "websocket:user_unread";

    /**
     * 存储websocket连接的用户
     */
    public static final String WEBSOCKET_USER_SET = "websocket:user_set";
}
