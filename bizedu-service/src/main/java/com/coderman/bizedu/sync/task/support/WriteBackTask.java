package com.coderman.bizedu.sync.task.support;

import com.alibaba.fastjson.JSON;
import com.coderman.bizedu.constant.PlanConstant;
import com.coderman.bizedu.constant.SyncConstant;
import com.coderman.bizedu.sync.context.SyncContext;
import com.coderman.bizedu.sync.exception.SyncException;
import com.coderman.bizedu.sync.executor.AbstractExecutor;
import com.coderman.bizedu.sync.plan.meta.MsgMeta;
import com.coderman.bizedu.sync.plan.meta.PlanMeta;
import com.coderman.bizedu.sync.sql.UpdateBuilder;
import com.coderman.bizedu.sync.sql.meta.SqlMeta;
import com.coderman.bizedu.sync.task.SyncConvert;
import com.coderman.bizedu.sync.task.SyncTask;
import com.coderman.bizedu.sync.task.TaskResult;
import com.coderman.bizedu.sync.util.SqlUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class WriteBackTask extends AbstractTask{


    private WriteBackTask(SyncTask syncTask){

        super.setSyncTask(syncTask);
    }


    @Override
    public TaskResult process(){

        TaskResult taskResult = new TaskResult();

        try {

            super.getExecutor().execute();
            taskResult.setRetry(false);
            taskResult.setCode(SyncConstant.TASK_CODE_SUCCESS);

        } catch (Throwable throwable) {

            taskResult.setRetry(false);
            taskResult.setCode(SyncConstant.TASK_CODE_FAIL);
            taskResult.setErrorMsg("回写消息状态失败,"+ throwable.getMessage());
        }

        return taskResult;
    }


    public static WriteBackTask build(SyncTask syncTask) throws SyncException {

        WriteBackTask writeBackTask = new WriteBackTask(syncTask);
        writeBackTask.createExecutor();

        return writeBackTask;
    }


    private void createExecutor() {

        MsgMeta msgMeta = super.getSyncTask().getMsgMeta();
        PlanMeta planMeta = super.getSyncTask().getPlanMeta();

        if(planMeta == null || planMeta.getDbMeta() == null || StringUtils.isBlank(planMeta.getDbMeta().getSrcDb())){

            log.error("get src db is null:"+(null == planMeta? null : planMeta.getCode())+",syncTask:"+ JSON.toJSONString(super.getSyncTask()));
            return;
        }

        String dbName = planMeta.getDbMeta().getSrcDb();


        // 构架执行器
        AbstractExecutor executor = AbstractExecutor.build(dbName);

        UpdateBuilder updateBuilder = UpdateBuilder.create(SyncContext.getContext().getDbType(dbName));

        updateBuilder.table("pub_mq_message");
        updateBuilder.column("deal_status");
        updateBuilder.column("ack_time");
        updateBuilder.inc("deal_count",1);
        updateBuilder.whereIn("uuid",1);

        List<Object> paramList =  new ArrayList<>();
        paramList.add(PlanConstant.RESULT_STATUS_SUCCESS);
        paramList.add(new Date());
        paramList.add(msgMeta.getMsgId());

        SqlMeta sqlMeta =  new SqlMeta();
        sqlMeta.setSql(updateBuilder.sql());
        sqlMeta.setParamList(SyncConvert.toArrayList(paramList));
        sqlMeta.setSqlType(SyncConstant.OPERATE_TYPE_UPDATE);

        executor.sql(sqlMeta);

        sqlMeta.setSql(SqlUtil.fillParam(sqlMeta,executor));

        super.setExecutor(executor);
    }
}
