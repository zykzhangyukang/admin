package com.coderman.bizedu.service.websocket.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.bizedu.constant.RedisConstant;
import com.coderman.bizedu.constant.WebSocketChannelEnum;
import com.coderman.bizedu.dto.websocket.WebsocketRedisMsg;
import com.coderman.bizedu.service.websocket.WebSocketService;
import com.coderman.redis.annotaion.RedisChannelListener;
import com.coderman.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    @Resource
    private SimpUserRegistry simpUserRegistry;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private RedisService redisService;

    /**
     * 发送消息到用户的方法
     *
     * @param senderId   发送方id
     * @param receiverId 接收方id
     * @param payload    消息内容
     * @return void
     */
    @Override
    public void sendToUser(Integer senderId, Integer receiverId, Object payload) {

        String sender = String.valueOf(senderId);
        String receiver = String.valueOf(receiverId);
        String destination = String.format(WebSocketChannelEnum.USER_SYS_MSG.getSubscribeUrl(), receiverId);
        SimpUser simpUser = simpUserRegistry.getUser(receiver);

        //如果接收者存在，则发送消息
        if (simpUser != null && StringUtils.isNoneBlank(simpUser.getName())) {

            simpMessagingTemplate.convertAndSend(destination, payload);
            log.info("sendToUser-websocket推送消息 destination => {} ,payload => {}", destination, JSON.toJSONString(payload));
        }
        //如果接收者在线，则说明接收者连接了集群的其他节点，需要通知接收者连接的那个节点发送消息
        else if (redisService.isSetMember(RedisConstant.WEBSOCKET_USER_SET, receiver, RedisDbConstant.REDIS_DB_DEFAULT)) {

            WebsocketRedisMsg<Object> websocketRedisMsg = new WebsocketRedisMsg<>(receiver, destination, payload);
            redisService.sendMessage(RedisConstant.CHANNEL_WEBSOCKET_NOTIFY, websocketRedisMsg);
        }
        //否则将消息存储到redis，等用户上线后主动拉取未读消息
        else {

            String listKey = RedisConstant.REDIS_UNREAD_MSG_PREFIX + ":" + receiver + ":" + destination;
            log.info("消息接收者:{}还未建立WebSocket连接，{} 发送的消息【{}】将被存储到Redis的【{}】列表中", receiver, sender, payload, listKey);
            this.redisService.setListAppend(listKey, payload, RedisDbConstant.REDIS_DB_DEFAULT);
        }
    }

    /**
     *
     * 广播主题消息
     * @param senderId   发送人id
     * @param payload 消息内容
     */
    @Override
    public void sendToTopic(Integer senderId, Object payload) {

        String destination = WebSocketChannelEnum.TOPIC_SYS_MSG.getSubscribeUrl();
        WebsocketRedisMsg<Object> websocketRedisMsg = new WebsocketRedisMsg<>(null, destination,payload);
        // 广播消息
        redisService.sendMessage(RedisConstant.CHANNEL_WEBSOCKET_NOTIFY, websocketRedisMsg);
    }


    /**
     * 订阅redis主题，解决分布式多节点websocket连接问题。
     *
     * @param websocketRedisMsg 消息内容
     */
    @RedisChannelListener(channelName = RedisConstant.CHANNEL_WEBSOCKET_NOTIFY, clazz = WebsocketRedisMsg.class)
    public void handWebSocketNotify(WebsocketRedisMsg<Object> websocketRedisMsg) {

        String receiver = websocketRedisMsg.getReceiver();
        Object content = websocketRedisMsg.getContent();
        String destination = websocketRedisMsg.getDestination();

        // 广播类型
        WebSocketChannelEnum webSocketChannelEnum = WebSocketChannelEnum.getBySubUrl(destination);
        if(WebSocketChannelEnum.TOPIC_SYS_MSG.equals(webSocketChannelEnum)){

            //  广播发送
            simpMessagingTemplate.convertAndSend(destination, content);
            log.info("handWebSocketNotify-websocket推送广播消息 destination => {} ,payload => {}", destination, JSON.toJSONString(content));

        }else {

            // 取出用户名并判断是否连接到当前应用节点的WebSocket
            SimpUser simpUser = simpUserRegistry.getUser(receiver);
            if (simpUser != null && StringUtils.isNoneBlank(simpUser.getName()) && StringUtils.isNotBlank(destination)) {

                //  给WebSocket客户端发送消息
                simpMessagingTemplate.convertAndSend(destination, content);
                log.info("handWebSocketNotify-websocket推送点对点消息 destination => {} ,payload => {}", destination, JSON.toJSONString(content));
            }
        }
    }
}
