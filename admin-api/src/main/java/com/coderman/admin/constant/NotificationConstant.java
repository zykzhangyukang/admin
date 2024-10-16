package com.coderman.admin.constant;

import com.coderman.api.anntation.ConstList;
import com.coderman.api.anntation.Constant;

@Constant
public interface NotificationConstant {

    /**
     * 消息分类
     */
    @ConstList(group = "notification_module",name = "全部消息")
    public static final String NOTIFICATION_MODULE_ALL = "all";
    @ConstList(group = "notification_module",name = "系统消息")
    public static final String NOTIFICATION_MODULE_SYSTEM = "system";
    @ConstList(group = "notification_module",name = "异常告警")
    public static final String NOTIFICATION_MODULE_ALARM = "alarm";
    @ConstList(group = "notification_module",name = "其他消息")
    public static final String NOTIFICATION_MODULE_OTHER = "other";



    /**
     * 消息来源
     */
    @ConstList(group = "notification_type",name = "登录欢迎")
    public static final String NOTIFICATION_LOGIN_WELCOME = "login_welcome";
}
