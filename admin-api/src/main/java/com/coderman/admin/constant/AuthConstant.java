package com.coderman.admin.constant;


import com.coderman.api.anntation.Constant;
import com.coderman.api.anntation.ConstList;

/**
 * @author coderman
 * @Title: 系统模块常量
 * @Description: TOD
 * @date 2022/2/2623:50
 */
@Constant
public interface AuthConstant {

    /**
     * 访问令牌过期时间 - 30分钟
     */
    Integer ACCESS_TOKEN_EXPIRED_SECOND = 30 * 60;
    /**
     * 刷新令牌过期时间 2 小时
     */
    Integer REFRESH_TOKEN_EXPIRED_SECOND = 60 * 60 * 2;

    /**
     * 用户会话redis key 前缀
     */
    String AUTH_ACCESS_TOKEN_NAME = "auth:access_token:";
    String AUTH_REFRESH_TOKEN_NAME = "auth:refresh_token:";
    String AUTH_DEVICE_TOKEN_NAME = "auth:device_token:";

    /**
     * 用户状态
     */
    String USER_STATUS_GROUP = "user_status_group";

    @ConstList(group = USER_STATUS_GROUP, name = "启用")
    Integer USER_STATUS_ENABLE = 1;
    @ConstList(group = USER_STATUS_GROUP, name = "禁用")
    Integer USER_STATUS_DISABLE = 0;

    /**
     * 日志模块
     */
    String LOG_MODULE_GROUP = "log_module_group";

    @ConstList(group = LOG_MODULE_GROUP, name = "用户模块")
    Integer LOG_MODULE_USER = 0;
    @ConstList(group = LOG_MODULE_GROUP, name = "角色模块")
    Integer LOG_MODULE_ROLE = 1;
    @ConstList(group = LOG_MODULE_GROUP, name = "资源模块")
    Integer LOG_MODULE_RESC = 2;
    @ConstList(group = LOG_MODULE_GROUP, name = "功能模块")
    Integer LOG_MODULE_FUNC = 3;

    /**
     * 日志等级
     */
    String LOG_LEVEL_GROUP = "log_level_group";

    @ConstList(group = LOG_LEVEL_GROUP, name = "普通")
    Integer LOG_LEVEL_NORMAL = 0;
    @ConstList(group = LOG_LEVEL_GROUP, name = "中等")
    Integer LOG_MODULE_MIDDLE = 1;
    @ConstList(group = LOG_LEVEL_GROUP, name = "高级")
    Integer LOG_MODULE_IMPORTANT = 2;

    /**
     * 项目常量
     */
    String PROJECT_DOMAIN = "project_domain";

    @ConstList(group = PROJECT_DOMAIN, name = "权限系统")
    String PROJECT_DOMAIN_AUTH = "auth";
    @ConstList(group = PROJECT_DOMAIN, name = "同步系统")
    String PROJECT_DOMAIN_SYNC= "sync";
    @ConstList(group = PROJECT_DOMAIN, name = "交易系统")
    String PROJECT_DOMAIN_TRADE= "trade";


    /**
     * 方法常量
     */
    String METHOD_TYPE = "method_type";

    @ConstList(group = METHOD_TYPE, name = "GET")
    String METHOD_TYPE_GET = "get";
    @ConstList(group = METHOD_TYPE, name = "POST")
    String METHOD_TYPE_POST = "post";
    @ConstList(group = METHOD_TYPE, name = "DELETE")
    String METHOD_TYPE_DELETE = "delete";
    @ConstList(group = METHOD_TYPE, name = "PUT")
    String METHOD_TYPE_PUT = "put";


    /**
     * 项目最顶级的功能父级id
     */
    Integer FUNC_ROOT_PARENT_ID = 0;

    /**
     * 功能类型
     */
    String FUNC_TYPE_GROUP = "func_type_group";

    @ConstList(group = FUNC_TYPE_GROUP, name = "目录")
    String FUNC_TYPE_DIR = "dir";
    @ConstList(group = FUNC_TYPE_GROUP, name = "按钮")
    String FUNC_TYPE_FUNC = "btn";

    /**
     * 是否隐藏菜单
     */
    String FUNC_HIDE_GROUP = "func_hide_group";

    @ConstList(group = FUNC_HIDE_GROUP, name = "隐藏")
    Integer FUNC_HIDE = 1;
    @ConstList(group = FUNC_HIDE_GROUP, name = "显示")
    Integer FUNC_SHOW = 0;
}
