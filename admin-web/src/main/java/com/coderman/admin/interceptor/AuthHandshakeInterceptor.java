package com.coderman.admin.interceptor;

import com.coderman.api.constant.RedisDbConstant;
import com.coderman.admin.constant.RedisConstant;
import com.coderman.admin.dto.common.AuthPrincipal;
import com.coderman.admin.service.user.UserService;
import com.coderman.admin.vo.user.AuthUserVO;
import com.coderman.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 建立连接时候的权限拦截器
 * @author zhangyukang
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Lazy(value = false)
public class AuthHandshakeInterceptor implements ChannelInterceptor {

    @Resource
    private UserService userApi;

    @Resource
    private RedisService redisService;

    @Override
    public Message<?> preSend(@NonNull Message<?> message,@NonNull MessageChannel channel) {

        // 不是第一次连接
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null || !StompCommand.CONNECT.equals(accessor.getCommand())) {

            return message;
        }

        List<String> nativeHeader = accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);
        String sessionId = accessor.getSessionId();
        if (CollectionUtils.isEmpty(nativeHeader)) {

            log.error("未登录系统，禁止连接WebSocket!,sessionId:{}",sessionId);
            return null;
        }

        String token = nativeHeader.get(0);
        AuthUserVO authUserVO = this.userApi.getUserByToken(token);
        if (authUserVO == null) {
            log.error("未登录系统，禁止连接WebSocket!, sessionId:{}", sessionId);
            return null;
        }

        Integer userId = authUserVO.getUserId();

        // 单节点会话
        accessor.setUser(new AuthPrincipal(userId));
        if (this.redisService.isSetMember(RedisConstant.WEBSOCKET_USER_SET, String.valueOf(userId), RedisDbConstant.REDIS_DB_DEFAULT)) {

            log.warn("同一个用户:{}, sessionId:{}, 不允许建立多个连接. 已重新连接WebSocket", userId, sessionId);
            return message;
        }

        // 将用户id存到Redis中
        this.redisService.addToSet(RedisConstant.WEBSOCKET_USER_SET, String.valueOf(userId), RedisDbConstant.REDIS_DB_DEFAULT);
        log.info("用户:{} 请求建立WebSocket连接, sessionId:{}", userId, sessionId);

        return message;
    }

}
