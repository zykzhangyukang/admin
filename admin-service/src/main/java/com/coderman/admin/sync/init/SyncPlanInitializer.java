package com.coderman.admin.sync.init;

import com.coderman.redis.annotaion.RedisChannelListener;
import com.coderman.admin.sync.constant.PlanConstant;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.model.PlanModel;
import com.coderman.admin.sync.plan.meta.PlanMeta;
import com.coderman.admin.sync.plan.parser.MetaParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Lazy(value = false)
@Component
@Slf4j
public class SyncPlanInitializer {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final static String sql = "select uuid,plan_code,src_db,dest_db,src_project,dest_project,plan_content,status, create_time,update_time from sync_plan where status= ?";

    public synchronized void init() {

        // 查询同步计划
        List<PlanModel> planModelList = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PlanModel.class), PlanConstant.STATUS_NORMAL);

        List<PlanMeta> resultList = new ArrayList<>();

        for (PlanModel planModel : planModelList) {

            try {

                PlanMeta planMeta = MetaParser.parse(planModel.getPlanContent());
                planMeta.setUuid(planModel.getUuid());
                resultList.add(planMeta);

            } catch (Exception E) {

                throw new RuntimeException("初始化同步计划失败:" + planModel.getPlanCode() + ",uuid:" + planModel.getUuid());
            }
        }

        // 加锁,阻塞所有同步任务
        SyncContext.getContext().addSyncLocker();

        // 等待进行中的同步任务完成
        SyncContext.getContext().waitSyncEnd();

        // 清理上下文环境中的数据
        MetaParser.clearAllPlanInfo();

        // 更新上下文中的数据
        for (PlanMeta planMeta : resultList) {

            MetaParser.putPlanToContext(planMeta);
        }

        // 释放锁
        SyncContext.getContext().releaseSyncLocker();
    }


    @RedisChannelListener(channelName = PlanConstant.TOPIC_REFRESH_PLAN)
    public void refreshSyncPlan(String messageContent) {

        try {

            this.init();
            log.info("refreshSyncPlan - 刷新同步计划完成 -> {}", messageContent);

        } catch (Exception e) {
            log.error("refreshSyncPlan - 刷新同步计划失败 -> {}", messageContent);
        }
    }

}
