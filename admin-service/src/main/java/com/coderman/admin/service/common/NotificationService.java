package com.coderman.admin.service.common;

import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.dto.common.NotificationPageDTO;
import com.coderman.api.vo.ResultVO;

import java.util.Map;

public interface NotificationService {

    /**
     * 通知指定用户 (持久化)
     *
     */
    public void saveNotifyToUser(NotificationDTO payload);

    /**
     *  推送系统消息
     *
     * @param payload
     */
    public void sendToTopic(NotificationDTO payload);

    /**
     * 推送用户消息
     */
    public void sendToUser(NotificationDTO payload);

    /**
     * 推送用户（指定会话）信息
     * @param payload
     */
    public void sendToUserSession(NotificationDTO payload);
    /**
     * 获取未读消息数
     *
     * @return
     */
    ResultVO<Long> getUnReadCount();

    /**
     * 标记已读
     * @param notificationId
     * @return
     */
    ResultVO<Void> updateNotificationRead(Integer notificationId);

    /**
     * 消息列表
     * @param notificationPageDTO
     * @return
     */
    ResultVO<Map<String,Object>> getNotificationPage(NotificationPageDTO notificationPageDTO);
}
