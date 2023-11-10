package com.coderman.sync.exception;

/**
 * @author zhangyukang
 */

public enum  ErrorCodeEnum {

    /**
     * 解析同步消息错误
     */
    PARSE_MSG_ERROR(1,"解析同步消息错误"),

    /**
     * 数据库配置不存在
     */
    DB_NOT_CONFIG(9,"数据库配置不存在"),

    /**
     * 无法获取数据库连接
     */
    DB_NOT_CONNECT(10,"无法获取数据库连接"),


    /**
     * mongo执行异常
     */
    DB_MONGO_ERROR(11,"mongo执行异常"),


    /**
     * 主键重复执行异常
     */
    DB_KEY_DUPLICATE(12, "主键重复执行异常"),

    /**
     * SQL参数多余
     */
    SQL_PARAM_EXCEED(20,"SQL参数多余"),

    /**
     * SQL参数数据不匹配
     */
    SQL_PARAM_NUM_NOT_MATCH(30,"SQL参数数据不匹配");


    /**
     * 错误code
     */
    private int code;

    /**
     * 错误描述
     */
    private String message;


    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
