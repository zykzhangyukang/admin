package com.coderman.admin.sync.jobhandler;

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

    @Override
    public ReturnT<String> execute(String param) {

        // 将 前20分钟 - 前5分钟的消息重新投递到mq
        int begin = -20;
        int end = -5;

        try {

            if(StringUtils.isNotBlank(param) && StringUtils.contains(param,"#")){
                begin = Integer.parseInt(param.split("#")[0]);
                end = Integer.parseInt(param.split("#")[1]);
            }

        }catch (Exception e){
            log.error("error parse use default:{},begin:{},end:{}",e.getMessage(),begin,end);
        }

        XxlJobLogger.log("前"+Math.abs(begin)+"分钟 - 前"+Math.abs(end)+"分钟的消息重新投递到mq");

        String[] databases = StringUtils.split(PropertyConfig.getConfigValue("pub_mq_message.db"), ",");

        for (String dbName : databases) {

            JdbcTemplate jdbcTemplate = SpringContextUtil.getBean(dbName + "_template");

            String sql = "select mq_message_id from pub_mq_message where send_status in ('sending','wait') and create_time < ?";

            // 1. 查询5分钟前处于发送中或者待发送的记录
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, DateUtils.addMinutes(new Date(), end));

            StringBuilder sb = new StringBuilder();

            sb.append("处理MQ消息发送失败记录.").append(dbName);

            if (CollectionUtils.isNotEmpty(resultList)) {

                List<Object> idList = resultList.stream().map(e -> e.get("mq_message_id")).collect(Collectors.toList());

                sb.append("预查询->").append(idList.size());

                // 2. 5分钟前处于发送中或者待发送的记录标记为失败
                sql = "update pub_mq_message set send_status = 'fail' where send_status in ('sending','wait') and mq_message_id in ";

                List<List<Object>> updateList = Lists.partition(idList, 50);

                List<String> updateSqlList = updateList.stream().map(tmp -> StringUtils.join(tmp, ",")).collect(Collectors.toList());

                int updateCount = 0;

                for (String tempSql : updateSqlList) {

                    tempSql = sql + "( select " + tempSql.replaceAll(",", " union select ") + ")";
                    tempSql += ";";

                    int rowCount = jdbcTemplate.update(tempSql);

                    updateCount += rowCount;
                }

                sb.append("实际更新->").append(updateCount);

            }

            // 3. 查询需处理的消息内容
            sql = "select msg_content from pub_mq_message where deal_count < ? and create_time > ? and create_time < ? and send_status = 'fail' and deal_status in ('wait','fail')";

            int retry = 5;
            Date startTime = DateUtils.addMinutes(new Date(), begin);
            Date endTime = DateUtils.addMinutes(new Date(), end);

            List<Map<String, Object>> resList = jdbcTemplate.queryForList(sql, retry, startTime, endTime);

            sb.append("需处理->").append(resList.size());

            if (CollectionUtils.isNotEmpty(resList)) {

                for (Map<String, Object> resultMap : resList) {

                    MsgTask msgTask = new MsgTask();

                    if (resultMap.containsKey("msg_content")) {

                        msgTask.setMsg(resultMap.get("msg_content").toString());

                    } else {

                        msgTask.setMsg(SqlUtil.getFieldName("msg_content"));
                    }

                    msgTask.setSource(SyncConstant.MSG_SOURCE_JOB);
                    SyncContext.getContext().addTaskToDelayQueue(msgTask);

                }

            }

            XxlJobLogger.log(sb.toString());
            log.info(sb.toString());
        }

        return ReturnT.SUCCESS;
    }
}
