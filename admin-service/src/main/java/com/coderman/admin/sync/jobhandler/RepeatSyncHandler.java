package com.coderman.admin.sync.jobhandler;

import com.coderman.admin.sync.config.SyncDbConfig;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.service.config.PropertyConfig;
import com.coderman.service.util.SpringContextUtil;
import com.coderman.admin.sync.task.base.MsgTask;
import com.coderman.admin.sync.util.SqlUtil;
import com.google.common.collect.Lists;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyukang
 */
@JobHandler(value = "repeatSyncHandler")
@Component
@Slf4j
public class RepeatSyncHandler extends IJobHandler {

    @Resource
    private SyncDbConfig syncDbConfig;

    private static final int DEFAULT_BEGIN = -20;
    private static final int DEFAULT_END = -5;
    private static final int BATCH_SIZE = 50;
    private static final int RETRY_LIMIT = 5;

    @Override
    public ReturnT<String> execute(String param) {
        int begin = DEFAULT_BEGIN;
        int end = DEFAULT_END;

        // 解析参数
        if (StringUtils.isNotBlank(param) && param.contains("#")) {
            try {
                String[] params = param.split("#");
                begin = Integer.parseInt(params[0]);
                end = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                logAndRecord("Error parsing parameters, using default values: " + begin + ", " + end, e);
            }
        }

        logAndRecord("重新投递前 " + Math.abs(begin) + " 分钟到前 " + Math.abs(end) + " 分钟的消息");

        // 获取数据库列表
        String[] databases = StringUtils.split(syncDbConfig.getPubMqMessage(), ",");

        for (String dbName : databases) {
            JdbcTemplate jdbcTemplate = SpringContextUtil.getBean(dbName + "_template");

            // 更新发送状态为'sending'或'wait'且在end时间之前的消息
            List<Object> messageIds = getMessageIds(jdbcTemplate, end);
            int updatedCount = updateMessageStatus(jdbcTemplate, messageIds);

            // 处理需要重新投递的消息
            List<Map<String, Object>> messagesToProcess = getMessagesToProcess(jdbcTemplate, begin, end);
            processMessages(messagesToProcess);

            logAndRecord("数据库: " + dbName + ", 预查询: " + messageIds.size() + ", 更新: " + updatedCount + ", 需处理: " + messagesToProcess.size());
        }

        return ReturnT.SUCCESS;
    }

    private List<Object> getMessageIds(JdbcTemplate jdbcTemplate, int end) {
        String sql = "SELECT mq_message_id FROM pub_mq_message WHERE send_status IN ('sending', 'wait') AND create_time < ?";
        return jdbcTemplate.queryForList(sql, DateUtils.addMinutes(new Date(), end))
                .stream()
                .map(row -> row.get("mq_message_id"))
                .collect(Collectors.toList());
    }

    private int updateMessageStatus(JdbcTemplate jdbcTemplate, List<Object> messageIds) {
        if (CollectionUtils.isEmpty(messageIds)) {
            return 0;
        }

        String baseSql = "UPDATE pub_mq_message SET send_status = 'fail' WHERE send_status IN ('sending', 'wait') AND mq_message_id IN ";
        int updatedCount = 0;

        for (List<Object> batch : Lists.partition(messageIds, BATCH_SIZE)) {
            String sql = baseSql + batch.stream().map(Object::toString).collect(Collectors.joining(", ", "(", ")"));
            updatedCount += jdbcTemplate.update(sql);
        }

        return updatedCount;
    }

    private List<Map<String, Object>> getMessagesToProcess(JdbcTemplate jdbcTemplate, int begin, int end) {
        String sql = "SELECT msg_content FROM pub_mq_message WHERE deal_count < ? AND create_time > ? AND create_time < ? AND send_status = 'fail' AND deal_status IN ('wait', 'fail')";
        return jdbcTemplate.queryForList(sql, RETRY_LIMIT, DateUtils.addMinutes(new Date(), begin), DateUtils.addMinutes(new Date(), end));
    }

    private void processMessages(List<Map<String, Object>> messages) {
        for (Map<String, Object> message : messages) {
            MsgTask msgTask = new MsgTask();
            msgTask.setMsg((String) message.getOrDefault("msg_content", SqlUtil.getFieldName("msg_content")));
            msgTask.setSource(SyncConstant.MSG_SOURCE_JOB);
            SyncContext.getContext().addTaskToDelayQueue(msgTask);
        }
    }

    private void logAndRecord(String message) {
        XxlJobLogger.log(message);
        //log.info(message);
    }

    private void logAndRecord(String message, Exception e) {
        XxlJobLogger.log(message);
        //log.error(message, e);
    }
}
