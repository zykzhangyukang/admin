package com.coderman.admin.sync.jobhandler;

import com.coderman.admin.sync.callback.CallbackContext;
import com.coderman.admin.sync.callback.meta.CallbackTask;
import com.coderman.admin.sync.config.SyncDbConfig;
import com.coderman.admin.sync.constant.PlanConstant;
import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.executor.AbstractExecutor;
import com.coderman.admin.sync.sql.SelectBuilder;
import com.coderman.admin.sync.sql.UpdateBuilder;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 回调消息重试
 *
 * @author ：zhangyukang
 * @date ：2024/08/05 18:00
 */
@JobHandler(value = "callbackHandler")
@Component
@Slf4j
public class CallbackHandler extends IJobHandler {

    @Resource
    private SyncDbConfig syncDbConfig;

    /**
     * 回调最大重试次数 16
     */
    public static final Integer MAX_CALLBACK_REPEAT_COUNT = 16;


    @SneakyThrows
    @Override
    public ReturnT<String> execute(String param) {

        XxlJobLogger.log("开始处理回调失败记录...");

        String dbStr = syncDbConfig.getPubCallback();

        for (String dbName : dbStr.split(",")) {

            if (StringUtils.isBlank(dbName)) {

                continue;
            }

            this.dealFailRecord(dbName, param);
        }

        XxlJobLogger.log("开始处理回调失败结束...");

        return ReturnT.SUCCESS;
    }

    private void dealFailRecord(String dbName, String param) {

        try {

            AbstractExecutor executor = AbstractExecutor.build(dbName);

            // 处理前10分钟的回调记录
            Date initDate = DateUtils.addMinutes(new Date(), -10);

            // 1. 查出callbackId, 前N分钟处于待回调, 回调中的记录, 重复次数为0.
            SelectBuilder callbackIdSelectBuilder = SelectBuilder.create(SyncContext.getContext().getDbType(dbName));
            callbackIdSelectBuilder.table("pub_callback").column("callback_id");
            callbackIdSelectBuilder.whereIn("status", 2);
            callbackIdSelectBuilder.whereEq("repeat_count");
            callbackIdSelectBuilder.whereLt("create_time");

            // 封装设置参数
            List<Object> paramList = new ArrayList<>();
            paramList.add(PlanConstant.CALLBACK_STATUS_ING);
            paramList.add(PlanConstant.CALLBACK_STATUS_WAIT);
            paramList.add(0);
            paramList.add(initDate);

            SqlMeta sqlMeta = new SqlMeta();
            sqlMeta.setSqlType(SyncConstant.OPERATE_TYPE_SELECT);
            sqlMeta.setSql(callbackIdSelectBuilder.sql());
            sqlMeta.setParamList(SyncConvert.toArrayList(paramList));

            executor.sql(sqlMeta);

            // 填充mongo语句参数
            sqlMeta.setSql(SqlUtil.fillParam(sqlMeta, executor));
            List<SqlMeta> callbackIds = executor.execute();

            // 2. 查询出的callbackId, 直接标记为失败, 直接重试
            if (callbackIds != null && callbackIds.size() == 1 && callbackIds.get(0).getResultList() != null && callbackIds.get(0).getResultList().size() > 0) {

                executor.clear();
                paramList.clear();

                // 构建语句
                UpdateBuilder updateBuilder = UpdateBuilder.create(SyncContext.getContext().getDbType(dbName));
                updateBuilder.table("pub_callback");
                updateBuilder.column("status");
                updateBuilder.column("send_time");
                updateBuilder.whereIn("callback_id", callbackIds.get(0).getResultList().size());

                // 封装参数
                paramList.add(PlanConstant.CALLBACK_STATUS_FAIL);
                paramList.add(DateUtils.addMinutes(new Date(), -10));

                for (Map<String, Object> callbackIdMap : callbackIds.get(0).getResultList()) {
                    paramList.add(callbackIdMap.get("callback_id"));
                }

                sqlMeta = new SqlMeta();
                sqlMeta.setSqlType(SyncConstant.OPERATE_TYPE_UPDATE);
                sqlMeta.setSql(updateBuilder.sql());
                sqlMeta.setParamList(SyncConvert.toArrayList(paramList));

                executor.sql(sqlMeta);

                sqlMeta.setSql(SqlUtil.fillParam(sqlMeta, executor));
                executor.execute();
            }

            // 3. 回调失败且重试次数小于N次的记录, 开始重试
            executor.clear();
            paramList.clear();
            SelectBuilder selectBuilder = SelectBuilder.create(SyncContext.getContext().getDbType(dbName));
            selectBuilder.table("pub_callback");
            selectBuilder.column("uuid").column("db_name").column("msg_content").column("dest_project");
            selectBuilder.whereEq("status");
            selectBuilder.whereLt("repeat_count");
            selectBuilder.whereLt("send_time");
            selectBuilder.whereGt("send_time");

            paramList.add(PlanConstant.CALLBACK_STATUS_FAIL);
            paramList.add(MAX_CALLBACK_REPEAT_COUNT);
            paramList.add(DateUtils.addMinutes(new Date(), -5));
            paramList.add(DateUtils.addMinutes(new Date(), -30));

            sqlMeta = new SqlMeta();
            sqlMeta.setSql(selectBuilder.sql());
            sqlMeta.setSqlType(SyncConstant.OPERATE_TYPE_SELECT);
            sqlMeta.setParamList(SyncConvert.toArrayList(paramList));

            executor.sql(sqlMeta);

            sqlMeta.setSql(SqlUtil.fillParam(sqlMeta, executor));
            List<SqlMeta> list = executor.execute();

            if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(list.get(0).getResultList())) {

                XxlJobLogger.log("处理回调失败记录. " + dbName + " -> 0");
            } else {
                XxlJobLogger.log("处理回调失败记录. " + dbName + " -> " + list.get(0).getResultList().size());

                for (Map<String, Object> resultMap : list.get(0).getResultList()) {

                    CallbackTask callbackTask = new CallbackTask();
                    callbackTask.setFirst(false);
                    if (resultMap.containsKey("db_name")) {

                        callbackTask.setProject(resultMap.get("dest_project").toString());
                        callbackTask.setUuid(resultMap.get("uuid").toString());
                        callbackTask.setMsg(resultMap.get("msg_content").toString());
                        callbackTask.setDb(resultMap.get("db_name").toString());

                    } else {

                        callbackTask.setProject(resultMap.get(SqlUtil.getFieldName("dest_project")).toString());
                        callbackTask.setUuid(resultMap.get("uuid").toString());
                        callbackTask.setMsg(resultMap.get(SqlUtil.getFieldName("msg_content")).toString());
                        callbackTask.setDb(resultMap.get(SqlUtil.getFieldName("db_name")).toString());
                    }

                    // 加入回调队列
                    CallbackContext.getCallbackContext().addTask(callbackTask);
                }
            }

        } catch (Throwable e) {
            XxlJobLogger.log("处理回调失败记录出错, 数据库:" + dbName + " , error: " + e.getMessage());
        }
    }
}
