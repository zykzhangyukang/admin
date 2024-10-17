package com.coderman.admin.service.notification;

import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.api.vo.ResultVO;

public interface NotificationService {

    /**
     * 通知指定用户 (持久化)
     *
     */
    public void saveNotifyToUser(NotificationDTO msg);

    /**
     *  推送系统消息
     *
     * @param senderId
     * @param payload
     */
    public void sendToTopic(Integer senderId, Object payload);

    /**
     * 推送用户消息
     */
    public void sendToUser(NotificationDTO dto);

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
