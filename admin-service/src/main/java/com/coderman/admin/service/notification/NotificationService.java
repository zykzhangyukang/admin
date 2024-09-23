package com.coderman.admin.service.notification;

import com.alibaba.fastjson.JSONObject;

public interface NotificationService {

    /**
     * 通知指定用户
     *
     * @param userId 用户id
     * @param data   消息体
     * @param type   消息类型
     */
    public void notify(Integer userId, JSONObject data, String type);
}
