package com.coderman.bizedu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author ：zhangyukang
 * @date ：2023/10/17 15:06
 */
@Configuration
public class WebSocketConfig {
    /**
     *  注入ServerEndpointExporter，
     *  这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
