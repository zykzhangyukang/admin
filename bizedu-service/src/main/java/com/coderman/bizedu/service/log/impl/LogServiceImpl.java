package com.coderman.bizedu.service.log.impl;

import com.coderman.bizedu.dao.log.LogDAO;
import com.coderman.bizedu.model.log.LogModel;
import com.coderman.bizedu.service.log.LogService;
import com.coderman.bizedu.utils.AuthUtil;
import com.coderman.bizedu.vo.user.AuthUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhangyukang
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogDAO logDAO;

    @Override
    public void saveLog(Integer logType,Integer userId,String username, String logInfo) {

        Assert.notNull(logType, "logType is null");
        logInfo = StringUtils.defaultString(logInfo);

        LogModel logModel = new LogModel();
        logModel.setLogType(logType);
        logModel.setLogInfo(logInfo);
        logModel.setUserId(userId);
        logModel.setUsername(username);
        logModel.setCreateTime(new Date());
        this.logDAO.insertSelective(logModel);
    }


    @Override
    public void saveLog(Integer loginLogType, String logInfo) {
        AuthUserVO current = AuthUtil.getCurrent();
        Assert.notNull(current , "当前登录用户不能为空！");
        this.saveLog(loginLogType , current.getUserId() ,current.getUsername() , logInfo);
    }


}
