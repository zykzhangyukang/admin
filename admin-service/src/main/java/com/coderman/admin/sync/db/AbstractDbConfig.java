package com.coderman.admin.sync.db;

import lombok.Data;

/**
 * 数据库连接基本配置
 * @author zhangyukang
 */
@Data
public abstract class AbstractDbConfig {


    /**
     * 连接字符串
     */
    private String url;


    /**
     * 账号
     */
    private String userName;


    /**
     * 密码
     */
    private String password;


    /**
     * bean Id
     */
    private String beanId;
}
