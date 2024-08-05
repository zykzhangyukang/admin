package com.coderman.admin.sync.jobhandler;

import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.model.ResultModel;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@JobHandler(value = "resultToEsHandler")
@Component
@Slf4j
public class ResultToEsHandler extends IJobHandler {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public ReturnT<String> execute(String param) {
        int begin = -20;
        int end = -5;

        // 解析参数并记录日志
        try {
            if (StringUtils.isNotBlank(param) && param.contains("#")) {
                String[] params = param.split("#");
                begin = Integer.parseInt(params[0]);
                end = Integer.parseInt(params[1]);
                log.info("Parsed parameters: begin = {}, end = {}", begin, end);
            }
        } catch (NumberFormatException e) {
            log.error("Error parsing parameters '{}'. Using default values: begin = {}, end = {}. Error: {}", param, begin, end, e.getMessage());
        }

        Date now = new Date();
        Date startTime = DateUtils.addMinutes(now, begin);
        Date endTime = DateUtils.addMinutes(now, end);

        final String sql = "SELECT uuid, plan_uuid, plan_code, plan_name, msg_src, mq_id, msg_id, msg_content, src_project, " +
                "dest_project, sync_content, msg_create_time, sync_time, status, error_msg, repeat_count, remark, sync_to_es " +
                "FROM sync_result WHERE sync_to_es = ? AND msg_create_time BETWEEN ? AND ?";

        List<ResultModel> resultModels = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ResultModel.class), 0, startTime, endTime);

        if (CollectionUtils.isNotEmpty(resultModels)) {
            log.info("Fetched {} records from database.", resultModels.size());
            resultModels.forEach(resultModel -> SyncContext.getContext().syncToEs(resultModel));
        } else {
            log.info("No records found for sync_to_es = 0 between {} and {}.", startTime, endTime);
        }

        int count = resultModels.size();
        XxlJobLogger.log("ES同步补偿器 - 刷新记录到ES总数: {}", count);
        log.info("ES同步补偿器 - 刷新记录到ES总数: {}", count);

        return ReturnT.SUCCESS;
    }
}
