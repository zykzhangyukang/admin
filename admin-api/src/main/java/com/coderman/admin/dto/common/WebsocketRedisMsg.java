package com.coderman.admin.dto.common;

import com.coderman.admin.constant.WebSocketChannelEnum;

/**
 * Redis中存储WebSocket消息
 *
 * @author zifangsky
 * @date 2018/10/16
 * @since 1.0.0
 */
public class WebsocketRedisMsg<T> {
    /**
     * 消息接收者的username
     */
    private String receiver;
    /**
     * 消息对应的订阅频道的CODE，参考{@link WebSocketChannelEnum}的subscribeUrl字段
     */
    private String destination;
    /**
     * 订阅的频道
     */
    private WebSocketChannelEnum webSocketChannelEnum;
    /**
     * 消息正文
     */
    private T content;

    public WebsocketRedisMsg() {
    }

    public WebsocketRedisMsg(String receiver, String destination, T content, WebSocketChannelEnum webSocketChannelEnum) {
        this.receiver = receiver;
        this.destination = destination;
        this.content = content;
        this.webSocketChannelEnum = webSocketChannelEnum;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getDestination() {
        return destination;
    }

    public WebSocketChannelEnum getWebSocketChannelEnum() {
        return webSocketChannelEnum;
    }

    public void setWebSocketChannelEnum(WebSocketChannelEnum webSocketChannelEnum) {
        this.webSocketChannelEnum = webSocketChannelEnum;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    @Override
    public String toString() {
        return "WebsocketRedisMsg{" +
                "receiver='" + receiver + '\'' +
                ", destination='" + destination + '\'' +
                ", webSocketChannelEnum=" + webSocketChannelEnum +
                ", content=" + content +
                '}';
    }
}
