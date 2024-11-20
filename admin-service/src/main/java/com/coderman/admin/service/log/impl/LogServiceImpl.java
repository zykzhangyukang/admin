package com.coderman.admin.service.log.impl;

import com.coderman.admin.service.log.LogService;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dao.log.LogDAO;
import com.coderman.admin.dto.log.LogPageDTO;
import com.coderman.admin.model.log.LogModel;
import com.coderman.admin.utils.AuthUtil;
import com.coderman.admin.vo.log.LogVO;
import com.coderman.admin.vo.user.AuthUserVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import com.coderman.service.service.BaseService;
import com.coderman.service.util.HttpContextUtil;
import com.coderman.service.util.IpUtil;
import com.coderman.sync.util.MsgBuilder;
import com.coderman.sync.util.ProjectEnum;
import com.coderman.sync.util.SyncUtil;
import com.coderman.sync.vo.PlanMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhangyukang
 */
@Service
public class LogServiceImpl extends BaseService implements LogService {

    @Resource
    private LogDAO logDAO;

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
        logModel.setIpAddress(IpUtil.getIpAddr());
        logModel.setLocation(IpUtil.getCityInfo());
        logModel.setDeviceInfo(IpUtil.getClientDeviceInfo(HttpContextUtil.getHttpServletRequest()));
        logDAO.insertSelectiveReturnKey(logModel);

        // 同步到日志系统
        SyncUtil.sync(
                MsgBuilder.create("insert_admin_sync_log", ProjectEnum.ADMIN, ProjectEnum.LOG)
                        .addIntList("insert_admin_sync_log", Collections.singletonList(logModel.getLogId()))
                        .build()
        );
        // 同步到销售系统
        SyncUtil.sync(
                MsgBuilder.create("insert_admin_sms_log", ProjectEnum.ADMIN, ProjectEnum.SMS)
                .addIntList("insert_admin_sms_log", Collections.singletonList(logModel.getLogId()))
                .build());
    }

    @Override
    @LogError(value = "保存日志信息")
    public void saveLog(Integer logModule, Integer logLevel, String logInfo) {

        AuthUserVO current = AuthUtil.getCurrent();
        if (current == null) {

            throw new BusinessException("请先登录后访问！");
        }

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
        if (StringUtils.isNotBlank(logPageDTO.getRealName())) {
            conditionMap.put("realName", logPageDTO.getRealName());
        }
        if (StringUtils.isNotBlank(logPageDTO.getIpAddress())) {
            conditionMap.put("ipAddress", logPageDTO.getIpAddress());
        }
        if (StringUtils.isNotBlank(logPageDTO.getDeviceInfo())) {
            conditionMap.put("deviceInfo", logPageDTO.getDeviceInfo());
        }
        if(logPageDTO.getStartTime()!=null){
            conditionMap.put("startTime", logPageDTO.getStartTime());
        }
        if(logPageDTO.getEndTime()!=null){
            conditionMap.put("endTime", logPageDTO.getEndTime());
        }

        if (StringUtils.isNotBlank(logPageDTO.getSortType()) && StringUtils.isNotBlank(logPageDTO.getSortField())) {
            conditionMap.put("sortType", logPageDTO.getSortType());
            conditionMap.put("sortField", logPageDTO.getSortField());
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
