package com.coderman.bizedu.service.log;

/**
 * @author zhangyukang
 */
public interface LogService {


    /**
     * 保存权限系统日志
     *
     * @param loginLogType 日志类型
     * @param userId 操作人id
     * @param username 操作人姓名
     * @param logInfo 日志信息
     */
    void saveLog(Integer loginLogType, Integer userId, String username,String logInfo);


    /**
     *
     * 保存权限系统日志
     *
     * @param loginLogType 日志类型
     * @param logInfo 日志信息
     */
    void saveLog(Integer loginLogType, String logInfo);
}
