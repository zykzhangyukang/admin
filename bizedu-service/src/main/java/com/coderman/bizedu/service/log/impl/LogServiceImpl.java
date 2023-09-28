package com.coderman.bizedu.service.log.impl;

import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.dao.log.LogDAO;
import com.coderman.bizedu.dto.log.LogPageDTO;
import com.coderman.bizedu.model.log.LogModel;
import com.coderman.bizedu.service.log.LogService;
import com.coderman.bizedu.utils.AuthUtil;
import com.coderman.bizedu.vo.log.LogVO;
import com.coderman.bizedu.vo.user.AuthUserVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhangyukang
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogDAO logDAO;

    @Override
    @LogError(value = "保存权限系统日志")
    public void saveLog(Integer logModule, Integer userId, String username, String realName, String logInfo) {

        Assert.notNull(logModule, "logModule is null");
        logInfo = StringUtils.defaultString(logInfo);

        LogModel logModel = new LogModel();
        logModel.setLogModule(logModule);
        logModel.setLogInfo(logInfo);
        logModel.setUserId(userId);
        logModel.setUsername(username);
        logModel.setRealName(realName);
        logModel.setCreateTime(new Date());
        this.logDAO.insertSelective(logModel);
    }

    @Override
    @LogError(value = "保存权限系统日志")
    public void saveLog(Integer logModule, Integer logLevel, Integer userId, String username, String realName, String logInfo) {

        logInfo = StringUtils.defaultString(logInfo);

        Assert.notNull(logModule, "logModule is null");
        Assert.notNull(logLevel, "logLevel is null");

        LogModel logModel = new LogModel();
        logModel.setLogModule(logModule);
        logModel.setLogInfo(logInfo);
        logModel.setUserId(userId);
        logModel.setLogLevel(logLevel);
        logModel.setUsername(username);
        logModel.setRealName(realName);
        logModel.setCreateTime(new Date());
        this.logDAO.insertSelective(logModel);
    }


    @Override
    @LogError(value = "保存权限系统日志")
    public void saveLog(Integer logModule, String logInfo) {
        AuthUserVO current = AuthUtil.getCurrent();
        Assert.notNull(current, "当前登录用户不能为空！");
        this.saveLog(logModule, current.getUserId(), current.getUsername(), current.getRealName(), logInfo);
    }

    @Override
    public void saveLog(Integer logModule, Integer logLevel, String logInfo) {
        AuthUserVO current = AuthUtil.getCurrent();
        Assert.notNull(current, "当前登录用户不能为空！");
        this.saveLog(logModule, logLevel, current.getUserId(), current.getUsername(), current.getRealName(), logInfo);
    }

    @Override
    @LogError(value = "查询权限系统日志")
    public ResultVO<PageVO<List<LogVO>>> page(@LogErrorParam LogPageDTO logPageDTO) {

        Map<String, Object> conditionMap = new HashMap<>(2);

        Integer currentPage = logPageDTO.getCurrentPage();
        Integer pageSize = logPageDTO.getPageSize();

        if (StringUtils.isNotBlank(logPageDTO.getLogModule())) {
            conditionMap.put("logModule", logPageDTO.getLogModule());
        }
        if (StringUtils.isNotBlank(logPageDTO.getLogLevel())) {
            conditionMap.put("logLevel", logPageDTO.getLogLevel());
        }
        if (StringUtils.isNotBlank(logPageDTO.getUsername())) {
            conditionMap.put("username", logPageDTO.getUsername());
        }

        List<LogVO> logVOList = new ArrayList<>();
        PageUtil.getConditionMap(conditionMap, currentPage, pageSize);

        Long count = this.logDAO.countPage(conditionMap);
        if (count > 0) {

            logVOList = this.logDAO.page(conditionMap);
        }

        return ResultUtil.getSuccessPage(LogVO.class, new PageVO<>(count, logVOList, currentPage, pageSize));
    }


}
