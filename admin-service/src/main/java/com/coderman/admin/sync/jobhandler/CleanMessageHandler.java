package com.coderman.admin.sync.jobhandler;

import com.coderman.admin.sync.config.SyncDbConfig;
import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.executor.AbstractExecutor;
import com.coderman.admin.sync.sql.meta.SqlMeta;
import com.coderman.admin.sync.task.SyncConvert;
import com.coderman.admin.sync.util.SqlUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 清理消息任务处理类
 */
@JobHandler(value = "cleanMessageHandler")
@Component
@Slf4j
public class CleanMessageHandler extends IJobHandler {

    @Resource
    private SyncDbConfig syncDbConfig;

    @SneakyThrows
    @Override
    public ReturnT<String> execute(String param) {

        // 保存近7天的本地消息
        Date ltTime = DateUtils.addDays(new Date(), -7);

        Set<String> databaseSets = new HashSet<>();
        List<String> messageDatabases = Arrays.asList(StringUtils.split(syncDbConfig.getPubMqMessage(), ","));
        List<String> callbackDatabases = Arrays.asList(StringUtils.split(syncDbConfig.getPubCallback(), ","));

        databaseSets.addAll(messageDatabases);
        databaseSets.addAll(callbackDatabases);

        for (String dbname : databaseSets) {
            try {
                // 删除本地消息表冗余数据
                deleteRedundantData(dbname, messageDatabases, ltTime, "pub_mq_message", "mq_message_id", "deal_status", "success", "create_time");

                // 删除回调消息表冗余数据
                deleteRedundantData(dbname, callbackDatabases, ltTime, "pub_callback", "callback_id", "status", "success", "create_time");

            } catch (Exception e) {
                String errorMsg = String.format("清除冗余数据失败: 数据库='%s', 错误信息='%s'", dbname, e.getMessage());
                XxlJobLogger.log(errorMsg);
                log.error(errorMsg, e);
            }
        }

        return ReturnT.SUCCESS;
    }

    /**
     * 删除冗余数据
     *
     * @param dbName       数据库名称
     * @param dbList       数据库列表
     * @param ltTime       时间点
     * @param tableName    表名
     * @param idColumn     ID 列名
     * @param statusColumn 状态列名
     * @param statusValue  状态值
     * @param timeColumn   时间列名
     * @throws Throwable
     */
    private void deleteRedundantData(String dbName, List<String> dbList, Date ltTime, String tableName, String idColumn, String statusColumn, String statusValue, String timeColumn) throws Throwable {

        if (!dbList.contains(dbName)) {
            return;
        }

        String dbType = SyncContext.getContext().getDbType(dbName);
        String batchDelSql;
        List<Object> paramList = new ArrayList<>();

        if (SyncConstant.DB_TYPE_MYSQL.equalsIgnoreCase(dbType)) {
            batchDelSql = String.format("DELETE FROM %s WHERE %s = ? AND %s < ? LIMIT ?", tableName, statusColumn, timeColumn);
            paramList.add(statusValue);
            paramList.add(ltTime);
            paramList.add(10000);
        } else if (SyncConstant.DB_TYPE_MSSQL.equalsIgnoreCase(dbType)) {
            batchDelSql = String.format("DELETE FROM %s WHERE %s IN (SELECT TOP %d %s FROM %s WHERE %s = ? AND %s < ?)", tableName, idColumn, 10000, idColumn, tableName, statusColumn, timeColumn);
            paramList.add(statusValue);
            paramList.add(ltTime);
        } else {
            String warnMsg = String.format("未知数据库类型 '%s'，跳过处理", dbType);
            log.warn(warnMsg);
            XxlJobLogger.log(warnMsg);
            return;
        }

        deleteLoop(dbName, tableName, batchDelSql, paramList);
    }

    /**
     * 循环删除
     *
     * @param dbName      数据库名称
     * @param batchDelSql 删除 SQL
     * @param paramList   参数列表
     * @throws Throwable
     */
    private void deleteLoop(String dbName, String tableName,String batchDelSql, List<Object> paramList) throws Throwable {

        int batchNum = 1;
        int deleteRows = -1;

        while (deleteRows != 0) {
            AbstractExecutor executor = AbstractExecutor.build(dbName);

            SqlMeta sqlMeta = new SqlMeta();
            sqlMeta.setSqlType(SyncConstant.OPERATE_TYPE_DELETE);
            sqlMeta.setSql(batchDelSql);
            sqlMeta.setParamList(SyncConvert.toArrayList(paramList));

            executor.sql(sqlMeta);
            sqlMeta.setSql(SqlUtil.fillParam(sqlMeta, executor));
            List<SqlMeta> resultList = executor.execute();

            if (CollectionUtils.isNotEmpty(resultList) && resultList.get(0) != null) {
                deleteRows = resultList.get(0).getAffectNum();
            } else {
                deleteRows = 0;
            }

            String logMsg = String.format("清除冗余数据消息记录，第%d批，数据库：%s.%s，删除条数：%d", batchNum, dbName, tableName,deleteRows);
            XxlJobLogger.log(logMsg);
            log.info(logMsg);

            batchNum++;
        }
    }
}
