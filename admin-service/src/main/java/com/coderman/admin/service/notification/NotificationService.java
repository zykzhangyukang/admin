package com.coderman.admin.service.notification;

import com.alibaba.fastjson.JSONObject;
import com.coderman.api.vo.ResultVO;

import java.util.Map;

public interface NotificationService {

    /**
     * 通知指定用户 (持久化)
     *
     * @param userId 用户id
     * @param data   消息体
     * @param type   消息类型
     */
    public void saveNotifyToUser(Integer userId, JSONObject data, String type);

    /**
     *  推送系统消息
     *
     * @param senderId
     * @param payload
     */
    public void sendToTopic(Integer senderId, Object payload);


    /**
     * 推送用户消息
     * @param receiverId
     * @param payload
     */
    public void sendToUser(Integer receiverId, Object payload);

    /**
     * 获取未读消息数
     *
     * @return
     */
    ResultVO<Long> getNotificationCount();

    /**
     * 标记已读
     * @param notificationId
     * @return
     */
    ResultVO<Void> updateNotificationRead(Integer notificationId);
}
