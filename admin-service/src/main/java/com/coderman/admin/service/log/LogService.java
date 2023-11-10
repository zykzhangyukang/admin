package com.coderman.admin.service.log;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dto.log.LogPageDTO;
import com.coderman.admin.vo.log.LogVO;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface LogService {


    /**
     * 保存权限系统日志 -  可设置日志等级
     *
     * @param logModule 日志类型
     * @param userId 操作人id
     * @param logLevel 日志等级
     * @param username 操作人账号
     * @param realName 操作人姓名
     * @param logInfo 日志信息
     */
    void saveLog(Integer logModule, Integer logLevel,Integer userId, String username,String realName,String logInfo);


    /**
     *
     * 保存权限系统日志 - 可设置日志等级
     *
     * @param logModule 日志类型
     * @param logInfo 日志信息
     * @param logLevel 日志等级
     */
    void saveLog(Integer logModule,Integer logLevel, String logInfo);


    /**
     * 查询权限系统日志
     * @param logPageDTO
     * @return
     */
    ResultVO<PageVO<List<LogVO>>> page(LogPageDTO logPageDTO);
}
