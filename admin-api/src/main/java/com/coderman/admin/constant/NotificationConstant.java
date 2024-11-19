package com.coderman.admin.constant;

import com.coderman.api.anntation.ConstList;
import com.coderman.api.anntation.Constant;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Constant
public interface NotificationConstant {

    /**
     * 消息分组关系
     */
    public Map<String, List<String>> NOTIFICATION_MAP =
            ImmutableMap.<String, List<String>>builder()
                    // 全部消息
                    .put(NotificationConstant.NOTIFICATION_MODULE_ALL, Arrays.asList(
                            NotificationConstant.NOTIFICATION_LOGIN_WELCOME,
                            NotificationConstant.NOTIFICATION_FUND_TIPS
                    ))
                    // 系统消息
                    .put(NotificationConstant.NOTIFICATION_MODULE_SYSTEM, Collections.singletonList(
                            NotificationConstant.NOTIFICATION_LOGIN_WELCOME
                    ))
                    // 告警消息
                    .put(NotificationConstant.NOTIFICATION_MODULE_ALARM, Collections.emptyList())
                    // 其他
                    .put(NotificationConstant.NOTIFICATION_MODULE_OTHER, Collections.singletonList(
                            NotificationConstant.NOTIFICATION_FUND_TIPS
                    ))
                    .build();


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
    @ConstList(group = "notification_type",name = "基金收益提醒")
    public static final String NOTIFICATION_FUND_TIPS = "fund_tips";
    @ConstList(group = "notification_type",name = "多设备登录")
    public static final String NOTIFICATION_DEVICE_CHECK = "device_check";

    /**
     * 消息是否已读
     */
    @ConstList(group = "is_read",name = "已读消息")
    public static final Integer IS_READ = 1;
    @ConstList(group = "is_read",name = "未读消息")
    public static final Integer IS_UNREAD = 0;
}
