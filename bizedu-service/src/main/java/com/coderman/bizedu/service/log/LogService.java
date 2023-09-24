package com.coderman.bizedu.service.log;

public interface LogService {


    /**
     * 保存权限系统日志
     *
     * @param loginLogType
     * @param userId
     * @param logInfo
     */
    void saveLog(String loginLogType, Integer relationId, Integer userId, String logInfo);
}
