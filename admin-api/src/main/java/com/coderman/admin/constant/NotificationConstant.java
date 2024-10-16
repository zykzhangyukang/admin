package com.coderman.admin.constant;

import com.coderman.api.anntation.ConstList;

public interface NotificationConstant {


    /**
     * 消息来源
     */
    @ConstList(group = "notification_type",name = "登录欢迎")
    public static final String NOTIFICATION_LOGIN_WELCOME = "login_welcome";
}
