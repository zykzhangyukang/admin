package com.coderman.bizedu.service.log.impl;

import com.coderman.bizedu.dao.log.LogDAO;
import com.coderman.bizedu.model.log.LogModel;
import com.coderman.bizedu.service.log.LogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogDAO logDAO;

    @Override
    public void saveLog(String logType, Integer relationId, Integer userId, String logInfo) {

        Assert.notNull(logType, "logType is null");
        logInfo = StringUtils.defaultString(logInfo);

        LogModel logModel = new LogModel();
        logModel.setLogType(logType);
        logModel.setLogInfo(logInfo);
        logModel.setRelationId(relationId);
        logModel.setUserId(userId);
        logModel.setCreateTime(new Date());
        this.logDAO.insertSelective(logModel);
    }

}
