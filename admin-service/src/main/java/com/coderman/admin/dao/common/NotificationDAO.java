package com.coderman.admin.dao.common;

import com.coderman.admin.model.common.NotificationExample;
import com.coderman.admin.model.common.NotificationModel;
import com.coderman.admin.vo.common.NotificationCountVO;
import com.coderman.admin.vo.common.NotificationVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NotificationDAO extends BaseDAO<NotificationModel, NotificationExample> {

    /**
     * 获取消息未读数
     * @param userId
     * @return
     */
    Long  getUnreadNotificationCount(@Param(value = "userId") Integer userId);

    /**
     * 分页条数
     * @param conditionMap
     * @return
     */
    Long countPage(Map<String, Object> conditionMap);

    /**
     * 消息列表
     * @param conditionMap
     * @return
     */
    List<NotificationVO> page(Map<String, Object> conditionMap);

    /**
     * 未读消息汇总
     * @param userId
     * @return
     */
    List<NotificationCountVO> getUnreadNotificationList(@Param(value = "userId") Integer userId);
}