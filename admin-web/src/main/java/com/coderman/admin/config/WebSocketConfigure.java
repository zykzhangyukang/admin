package com.coderman.admin.config;

import com.coderman.admin.interceptor.AuthChannelInterceptor;
import com.coderman.admin.interceptor.AuthHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;

/**
 * @author ：zhangyukang
 * @date ：2023/10/17 15:06
 */
@Configuration
@EnableWebSocketMessageBroker
@DependsOn(value = {"authHandshakeInterceptor","authChannelInterceptor"})
public class WebSocketConfigure implements WebSocketMessageBrokerConfigurer {

    @Resource
    private AuthHandshakeInterceptor authHandshakeInterceptor;
    @Resource
    private AuthChannelInterceptor authChannelInterceptor;


    // 客户端订阅服务器地址的前缀信息
    private static final String[] CLIENT_DES_PREFIX = new String[]{
            "/topic",
            "/user",
    };

    // 客户端发送消息给服务端的地址允许前缀
    private static final String[] SERVER_DES_PREFIX = new String[]{
            "/app"
    };


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sys_websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(CLIENT_DES_PREFIX);
        config.setApplicationDestinationPrefixes(SERVER_DES_PREFIX);
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authHandshakeInterceptor , authChannelInterceptor);
    }

}
