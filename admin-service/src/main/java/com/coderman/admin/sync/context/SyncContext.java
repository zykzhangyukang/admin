package com.coderman.admin.sync.context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.admin.sync.model.ResultModel;
import com.coderman.admin.sync.plan.meta.PlanMeta;
import com.coderman.admin.sync.plan.meta.TableMeta;
import com.coderman.admin.sync.task.SyncTask;
import com.coderman.admin.sync.task.base.BaseTask;
import com.coderman.admin.sync.thread.ResultToEsThread;
import com.coderman.admin.sync.thread.SyncRetryThread;
import com.coderman.service.util.SpringContextUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangyukang
 */
@Data
@Lazy(value = false)
@Component
public class SyncContext {

    private final static Logger logger = LoggerFactory.getLogger(SyncContext.class);
    /**
     * 同步重试线程
     */
    @Resource
    private SyncRetryThread syncRetryThread;
    /**
     * 同步es线程
     */
    @Resource
    private ResultToEsThread resultToEsThread;
    /**
     * 同步上下文
     */
    private static SyncContext syncContext;
    /**
     * 项目代号与数据库之间的关系
     */
    private Map<String, String> projectDbMap = new ConcurrentHashMap<>();
    /**
     * 同步table原始数据
     */
    private Map<String, TableMeta> tableMetaMap = new ConcurrentHashMap<>();
    /**
     * 数据库名称与类型之间的关系
     */
    private Map<String, String> dbTypeMap = new ConcurrentHashMap<>();
    /**
     * 同步计划原始数据
     */
    private Map<String, PlanMeta> planMetaMap = new ConcurrentHashMap<>();
    /**
     * 是否锁定同步任务,默认锁定
     */
    private volatile boolean lockSyncTask = true;
    /**
     * 正在进行中的同步任务数量
     */
    private AtomicInteger syncTaskCount = new AtomicInteger(0);


    public static SyncContext getContext() {
        return syncContext;
    }


    /**
     * 设置项目与数据库连接之间的关系
     */
    public void relateDbType(String dbname, String type) {
        this.dbTypeMap.put(dbname, type);
    }


    public void relateProjectDb(String project, String db) {
        this.projectDbMap.put(project, db);
    }


    @PostConstruct
    public void init() {
        SyncContext.syncContext = SpringContextUtil.getBean(SyncContext.class);
    }


    public String getDbType(String dbName) {
        if(StringUtils.isBlank(dbName)){
            return null;
        }
        return this.dbTypeMap.get(dbName);
    }


    public TableMeta getTableMeta(String planCode, String tableCode) {

        return this.tableMetaMap.get(planCode + "@" + tableCode);
    }

    /**
     * 同步数据
     *
     * @param msg        消息内容
     * @param mqMsgId    mq消息id
     * @param msgSrc     消息来源
     * @param retryTimes 重试次数
     * @return 同步结果
     */
    public String syncData(String msg, String mqMsgId, String msgSrc, int retryTimes) {

        String result = SyncConstant.SYNC_RETRY;

        // 标记开始同步任务
        this.startSync();

        try {

            // 构建同步任务
            result = SyncTask.build(msg, mqMsgId, msgSrc, retryTimes).sync();

        } catch (Exception e) {

            logger.error("同步数据出错: {}",e.getMessage());

        } finally {

            this.endSync();
        }

        return result;
    }


    /**
     * 同步记录同步到es
     *
     * @param resultModel 同步记录
     */
    public void syncToEs(ResultModel resultModel) {
        this.resultToEsThread.add(resultModel);
    }

    /**
     * 结束同步任务
     *
     * @return 同步任务数量
     */
    private int endSync() {

        return this.syncTaskCount.decrementAndGet();
    }


    /**
     * 开始同步
     */
    private int startSync() {

        int count = 0;

        // 被锁定,暂停后继续执行
        while (this.lockSyncTask) {

            logger.warn("暂停同步任务,等待同步计划刷新!");

            if (count > 20) {

                logger.error("同步任务锁定超时,自动结束!");
            }

            count++;

            try {

                TimeUnit.MILLISECONDS.sleep(500);

            } catch (InterruptedException e) {

                logger.error("暂停同步任务失败! :{}", e.getMessage(), e);
            }
        }

        return this.syncTaskCount.incrementAndGet();
    }

    public PlanMeta getPlanMeta(String planCode) {
        return this.planMetaMap.get(planCode);
    }

    public void addSyncLocker() {
        this.lockSyncTask = true;
    }

    public void waitSyncEnd() {

        int count = 0;

        while (this.syncTaskCount.get() > 0) {

            if (count > 20) {

                logger.error("等待同步任务超时!");
                break;
            }

            count++;

            try {

                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {

                logger.error("等待同步任务结束失败!");
            }
        }
    }

    public void clearProjectDb() {
        this.projectDbMap.clear();
    }

    public void clearPlanMeta() {
        this.planMetaMap.clear();

        this.tableMetaMap.clear();
    }

    public void addPlanMeta(String code, PlanMeta planMeta) {

        this.planMetaMap.put(code, planMeta);

        for (TableMeta tableMeta : planMeta.getTableMetas()) {

            this.tableMetaMap.put(code + "@" + tableMeta.getCode(), tableMeta);
        }
    }

    public void releaseSyncLocker() {
        this.lockSyncTask = false;
    }


    public void addTaskToDelayQueue(BaseTask task) {

        logger.info("addTaskToDelayQueue-start:{}", JSON.toJSONString(task, SerializerFeature.IgnoreNonFieldGetter));


        task.setDelayTime(30);

        this.syncRetryThread.addTask(task);

        logger.info("addTaskToDelayQueue-end");
    }

    public SyncTask buildSyncTask(String msg, String mqMsgId, String msgSrc, int retryTimes) {

        SyncTask syncTask = null;

        // 标记开始同步任务
        this.startSync();

        try {

            // 构建同步任务
            syncTask = SyncTask.build(msg, mqMsgId, msgSrc, retryTimes);

        } catch (Exception e) {

            logger.error("同步数据出错:", e);

        } finally {

            this.endSync();
        }

        return syncTask;
    }

    public String getDbByProject(String srcProject) {
        if(StringUtils.isBlank(srcProject)){
            return null;
        }
        return this.projectDbMap.get(srcProject);
    }
}
