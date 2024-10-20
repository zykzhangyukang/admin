package com.coderman.admin.service.common;

import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.dto.common.NotificationPageDTO;
import com.coderman.admin.model.common.NotificationModel;
import com.coderman.admin.vo.common.NotificationVO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;

import java.util.List;

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
    ResultVO<PageVO<List<NotificationVO>>> getNotificationPage(NotificationPageDTO notificationPageDTO);
}
