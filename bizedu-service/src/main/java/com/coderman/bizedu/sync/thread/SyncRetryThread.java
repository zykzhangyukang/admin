package com.coderman.bizedu.sync.thread;

import com.alibaba.fastjson.JSON;
import com.coderman.bizedu.model.sync.ResultModel;
import com.coderman.bizedu.vo.sync.CompareVO;
import com.coderman.service.util.SpringContextUtil;
import com.coderman.bizedu.constant.PlanConstant;
import com.coderman.bizedu.constant.SyncConstant;
import com.coderman.bizedu.sync.context.SyncContext;
import com.coderman.bizedu.sync.es.EsService;
import com.coderman.bizedu.sync.service.result.ResultService;
import com.coderman.bizedu.sync.task.SyncTask;
import com.coderman.bizedu.sync.task.TaskResult;
import com.coderman.bizedu.sync.task.base.BaseTask;
import com.coderman.bizedu.sync.task.base.MsgTask;
import com.coderman.bizedu.sync.task.support.SyncDataTask;
import com.coderman.bizedu.sync.task.support.WriteBackTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangyukang
 */
@Component
@Lazy(false)
@Slf4j
public class SyncRetryThread {


    private final DelayQueue<BaseTask> queue = new DelayQueue<>();

    @Resource
    private ResultService resultService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Resource
    private EsService esService;


    private void process(BaseTask baseTask) {

        if (baseTask instanceof WriteBackTask) {

            ((WriteBackTask) baseTask).process();

        } else if (baseTask instanceof MsgTask) {

            MsgTask msgTask = (MsgTask) baseTask;
            SyncContext.getContext().syncData(msgTask.getMsg(), StringUtils.EMPTY, msgTask.getSource(), 0);

        } else if (baseTask instanceof SyncDataTask) {

            SyncDataTask syncDataTask = (SyncDataTask) baseTask;
            ResultModel resultModel = syncDataTask.getSyncTask().getResultModel();

            boolean success = true;

            try {

                List<CompareVO> compareVOList = this.resultService.selectTableData(resultModel.getMsgContent(), true).getResult();

                for (CompareVO compareVO : compareVOList) {

                    List<Object> srcList = compareVO.getSrcResultList();
                    List<Object> destList = compareVO.getDestResultList();

                    if (srcList.size() != destList.size()) {

                        success = false;
                        break;
                    }

                    boolean isBreak = false;

                    for (int i = 0; i < srcList.size(); i++) {

                        if (null == srcList.get(i) && null == destList.get(i)) {

                            continue;
                        }

                        if (null != srcList.get(i) && !srcList.get(i).equals(destList.get(i))) {

                            isBreak = true;
                            break;
                        }

                        if (null != destList.get(i) && !destList.get(i).equals(srcList.get(i))) {

                            isBreak = true;
                            break;
                        }
                    }

                    if (isBreak) {

                        success = false;
                        break;
                    }

                }

            } catch (Throwable throwable) {

                log.error("主键重复修正同步结果失败." + resultModel.getMsgContent(), throwable);
                success = false;
            }

            if (success) {

                log.error("主键重复修正同步结果成功." + resultModel.getMsgId());

                String remark = "主键重复,修正同步结果成功";

                JdbcTemplate jdbcTemplate = SpringContextUtil.getBean(JdbcTemplate.class);

                jdbcTemplate.update("update sync_result set status=?,remark=? where msg_id=?", preparedStatement -> {

                    preparedStatement.setString(1, PlanConstant.RESULT_STATUS_SUCCESS);
                    preparedStatement.setString(2, remark);
                    preparedStatement.setString(3, resultModel.getMsgId());
                });

                this.changeSyncResult(resultModel);

            } else {

                SyncContext.getContext().addTaskToDelayQueue(syncDataTask);
                log.error("主键重复修正同步结果失败." + resultModel.getMsgContent());
            }

        } else {

            log.error("process_no:" + JSON.toJSONString(baseTask));
        }

    }


    private void changeSyncResult(ResultModel resultModel) {

        try {

            SyncTask syncTask = SyncContext.getContext().buildSyncTask(resultModel.getMsgContent(), StringUtils.EMPTY, SyncConstant.MSG_SOURCE_HANDLE, 0);

            WriteBackTask writeBackTask = WriteBackTask.build(syncTask);

            TaskResult taskResult = writeBackTask.process();

            if (SyncConstant.TASK_CODE_FAIL.equalsIgnoreCase(taskResult.getCode())) {

                SyncContext.getContext().addTaskToDelayQueue(writeBackTask);
            } else {

                this.esService.updateSyncResultSuccess(resultModel, "主键重复修正");
            }

        } catch (Exception e) {

            log.error("修改同步结果失败", e);
        }

    }

    public void addTask(BaseTask baseTask) {
        this.queue.add(baseTask);
    }

    @PostConstruct
    public void init() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                log.info("同步重试线程启动");

                while (true) {

                    try {

                        final BaseTask task = queue.take();

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    process(task);

                                } catch (Throwable throwable) {

                                    SyncContext.getContext().addTaskToDelayQueue(task);
                                }

                            }
                        });


                    } catch (InterruptedException e) {


                        throw new RuntimeException(e);
                    }

                }

            }
        });

        thread.setName("同步失败重试线程");
        thread.setDaemon(true);
        thread.start();

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {

                if ("同步失败重试线程".equals(t.getName())) {

                    init();
                }
            }
        });

    }


    @PreDestroy
    private void destroy() {
        this.executorService.shutdown();
    }

}
