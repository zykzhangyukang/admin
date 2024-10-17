package com.coderman.admin.service.notification.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.constant.RedisConstant;
import com.coderman.admin.constant.WebSocketChannelEnum;
import com.coderman.admin.dao.notification.NotificationDAO;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.dto.common.WebsocketRedisMsg;
import com.coderman.admin.model.notification.NotificationModel;
import com.coderman.admin.service.notification.NotificationService;
import com.coderman.admin.utils.AuthUtil;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.redis.annotaion.RedisChannelListener;
import com.coderman.redis.service.RedisService;
import com.coderman.service.anntation.LogError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author ：zhangyukang
 * @date ：2024/09/23 18:06
 */
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationDAO notificationDAO;
    @Resource
    private SimpUserRegistry simpUserRegistry;
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;
    @Resource
    private RedisService redisService;

    @Override
    public void saveNotifyToUser(NotificationDTO payload) {

        String msgType = payload.getType();
        Assert.notNull(msgType, "消息类型不能为空!");

        // 保存消息
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setCreateTime(new Date());
        notificationModel.setNotificationType(msgType);
        notificationModel.setIsRead(0);
        notificationModel.setData(JSON.toJSONString(payload));
        notificationModel.setUserId(payload.getUserId());
        this.notificationDAO.insertSelective(notificationModel);
        // 实时提示
        this.sendToUser(payload);
    }

    /**
     * 发送消息到用户的方法
     *
     * @return void
     */
    @Override
    public void sendToUser(NotificationDTO payload) {

        Integer receiverId = payload.getUserId();

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
            redisService.sendTopicMessage(RedisConstant.CHANNEL_WEBSOCKET_NOTIFY, websocketRedisMsg);
        } else {
            log.info("用户:{} 离线不提示信息:{}", receiver, JSON.toJSONString(payload));
        }
    }

    @Override
    @LogError(value = "获取未读消息数")
    public ResultVO<Long> getNotificationCount() {

        Integer userId = AuthUtil.getUserId();
        Long unreadNotificationCount = this.notificationDAO.getUnreadNotificationCount(userId);
        return ResultUtil.getSuccess(Long.class, unreadNotificationCount);
    }

    @Override
    @LogError(value = "标记已读")
    public ResultVO<Void> updateNotificationRead(Integer notificationId) {

        NotificationModel notificationModel = this.notificationDAO.selectByPrimaryKey(notificationId);
        Assert.notNull(notificationModel , "数据不存在,请刷新页面重试!");

        if(Objects.equals(notificationModel.getIsRead() , 0)){

            NotificationModel update = new NotificationModel();
            update.setNotificationId(notificationId);
            update.setIsRead(1);
            this.notificationDAO.updateByPrimaryKeySelective(update);
        }

        return ResultUtil.getSuccess();
    }

    /**
     * 广播主题消息
     *
     * @param senderId 发送人id
     * @param payload  消息内容
     */
    @Override
    public void sendToTopic(Integer senderId, Object payload) {

        String destination = WebSocketChannelEnum.TOPIC_SYS_MSG.getSubscribeUrl();
        WebsocketRedisMsg<Object> websocketRedisMsg = new WebsocketRedisMsg<>(StringUtils.EMPTY, destination, payload);
        // 广播消息
        redisService.sendTopicMessage(RedisConstant.CHANNEL_WEBSOCKET_NOTIFY, websocketRedisMsg);
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
        if (WebSocketChannelEnum.TOPIC_SYS_MSG.equals(webSocketChannelEnum)) {

            //  广播发送
            simpMessagingTemplate.convertAndSend(destination, content);
            log.info("handWebSocketNotify-websocket推送广播消息 destination => {} ,payload => {}", destination, JSON.toJSONString(content));

        } else {

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
