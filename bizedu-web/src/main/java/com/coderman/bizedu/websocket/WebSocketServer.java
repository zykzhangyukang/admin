package com.coderman.bizedu.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * @author ：zhangyukang
 * @date ：2023/10/17 15:24
 */
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer extends WebSocketServerSupport {

    //可打开websocket在线测试地址，输入地址ws://localhost:8088/websocket/1即可进行测试

}

