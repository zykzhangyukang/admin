package com.coderman.admin.dao.notification;

import com.coderman.admin.model.notification.NotificationExample;
import com.coderman.admin.model.notification.NotificationModel;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

public interface NotificationDAO extends BaseDAO<NotificationModel, NotificationExample> {

    /**
     * 获取消息未读数
     * @param userId
     * @return
     */
    Long  getUnreadNotificationCount(@Param(value = "userId") Integer userId);
}