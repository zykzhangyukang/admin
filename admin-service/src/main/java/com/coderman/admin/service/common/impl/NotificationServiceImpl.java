package com.coderman.admin.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.constant.NotificationConstant;
import com.coderman.admin.constant.RedisConstant;
import com.coderman.admin.constant.WebSocketChannelEnum;
import com.coderman.admin.dao.common.NotificationDAO;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.dto.common.NotificationPageDTO;
import com.coderman.admin.dto.common.WebsocketRedisMsg;
import com.coderman.admin.model.common.NotificationModel;
import com.coderman.admin.service.common.NotificationService;
import com.coderman.admin.utils.AuthUtil;
import com.coderman.admin.vo.common.NotificationCountVO;
import com.coderman.admin.vo.common.NotificationVO;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.redis.annotaion.RedisChannelListener;
import com.coderman.redis.service.RedisService;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

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
        else if (redisService.isSetMember(RedisConstant.WEBSOCKET_USER_SET, receiver, RedisDbConstant.REDIS_DB_AUTH)) {

            WebsocketRedisMsg<Object> websocketRedisMsg = new WebsocketRedisMsg<>(receiver, destination, payload, WebSocketChannelEnum.USER_SYS_MSG);
            redisService.sendTopicMessage(RedisConstant.CHANNEL_WEBSOCKET_NOTIFY, websocketRedisMsg);
        } else {
            log.info("用户:{} 离线不提示信息:{}", receiver, JSON.toJSONString(payload));
        }
    }

    @Override
    @LogError(value = "推送用户（指定会话）信息")
    public void sendToUserSession(NotificationDTO payload) {

        String sessionKey = Optional.ofNullable(payload.getSessionKey()).orElse(AuthUtil.getToken());

        String destination = String.format(WebSocketChannelEnum.USER_CHECK_MSG.getSubscribeUrl(), sessionKey);
        WebsocketRedisMsg<Object> websocketRedisMsg = new WebsocketRedisMsg<>(sessionKey, destination, payload, WebSocketChannelEnum.USER_CHECK_MSG);
        redisService.sendTopicMessage(RedisConstant.CHANNEL_WEBSOCKET_NOTIFY, websocketRedisMsg);
    }

    @Override
    @LogError(value = "获取未读消息数")
    public ResultVO<Long> getUnReadCount() {

        Integer userId = AuthUtil.getUserId();
        Assert.notNull(userId, "用户未登录!");

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

    @Override
    @LogError(value = "消息通知列表")
    public ResultVO<Map<String,Object>> getNotificationPage(@LogErrorParam NotificationPageDTO notificationPageDTO) {
        // 构建查询条件
        Map<String, Object> conditionMap = new HashMap<>(5);
        Assert.hasText(notificationPageDTO.getModule(), "请选择消息模块！");
        conditionMap.put("userId", AuthUtil.getUserId());
        conditionMap.put("module", notificationPageDTO.getModule());
        conditionMap.put("notificationTypes", NotificationConstant.NOTIFICATION_MAP.get(notificationPageDTO.getModule()));

        if (Objects.nonNull(notificationPageDTO.getIsRead())) {
            conditionMap.put("isRead", notificationPageDTO.getIsRead());
        }
        if (StringUtils.isNotBlank(notificationPageDTO.getSortType())) {
            conditionMap.put("sortType", notificationPageDTO.getSortType());
            conditionMap.put("sortField", notificationPageDTO.getSortField());
        }

        PageUtil.getConditionMap(conditionMap, notificationPageDTO.getCurrentPage(), notificationPageDTO.getPageSize());

        // 获取消息列表
        List<NotificationVO> notificationVOList = new ArrayList<>();
        Long count = notificationDAO.countPage(conditionMap);
        if (count > 0) {
            notificationVOList = notificationDAO.page(conditionMap);
            for (NotificationVO notificationVO : notificationVOList) {
                // 解析消息内容
                if (StringUtils.isNotBlank(notificationVO.getData())) {
                    try {
                        JSONObject jsonObject = JSON.parseObject(notificationVO.getData());
                        notificationVO.setMessage(jsonObject.getString("message"));
                    } catch (Exception ignore) {
                    }
                }
            }
        }
        // 获取未读数量
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<NotificationCountVO> countVOList = notificationDAO.getUnreadNotificationList(AuthUtil.getUserId());
        for (String module : NotificationConstant.NOTIFICATION_MAP.keySet()) {
            Long unReadCount = countVOList.stream()
                    .filter(countVO -> NotificationConstant.NOTIFICATION_MAP.get(module).contains(countVO.getNotificationType()))
                    .mapToLong(NotificationCountVO::getUnReadCount)
                    .sum();
            resultMap.put(module, unReadCount);
        }

        PageVO<List<NotificationVO>> pageInfo = new PageVO<>(count, notificationVOList, notificationPageDTO.getCurrentPage(), notificationPageDTO.getPageSize());
        resultMap.put("pageInfo", pageInfo);
        return ResultUtil.getSuccessMap(Map.class, resultMap);
    }


    /**
     * 广播主题消息
     *
     * @param payload  消息内容
     */
    @Override
    @LogError(value = "广播主题消息")
    public void sendToTopic(NotificationDTO payload) {

        String destination = WebSocketChannelEnum.TOPIC_SYS_MSG.getSubscribeUrl();
        WebsocketRedisMsg<Object> websocketRedisMsg = new WebsocketRedisMsg<>(StringUtils.EMPTY, destination, payload, WebSocketChannelEnum.TOPIC_SYS_MSG);
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
        WebSocketChannelEnum socketChannelEnum = websocketRedisMsg.getWebSocketChannelEnum();

        if (WebSocketChannelEnum.TOPIC_SYS_MSG.equals(socketChannelEnum)) {

            //  广播发送
            simpMessagingTemplate.convertAndSend(destination, content);
            log.debug("handWebSocketNotify-websocket推送广播消息 destination => {} ,payload => {}", destination, JSON.toJSONString(content));

        } else if(WebSocketChannelEnum.USER_SYS_MSG.equals(socketChannelEnum)){

            // 取出用户名并判断是否连接到当前应用节点的WebSocket
            SimpUser simpUser = simpUserRegistry.getUser(receiver);
            if (simpUser != null && StringUtils.isNoneBlank(simpUser.getName()) && StringUtils.isNotBlank(destination)) {

                //  给WebSocket客户端发送消息
                simpMessagingTemplate.convertAndSend(destination, content);
                log.debug("handWebSocketNotify-websocket推送点对点消息 destination => {} ,payload => {}", destination, JSON.toJSONString(content));
            }
        }else if(WebSocketChannelEnum.USER_CHECK_MSG.equals(socketChannelEnum)){

            simpMessagingTemplate.convertAndSend(destination, content);
            log.debug("handWebSocketNotify-websocket推送多用户设备下线通知 destination => {} ,payload => {}", destination, JSON.toJSONString(content));
        }
    }
}
