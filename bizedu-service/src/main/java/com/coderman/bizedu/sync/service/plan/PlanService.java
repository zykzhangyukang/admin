package com.coderman.bizedu.sync.service.plan;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.dto.sync.PlanPageDTO;
import com.coderman.bizedu.dto.sync.PlanSaveDTO;
import com.coderman.bizedu.dto.sync.PlanUpdateDTO;
import com.coderman.bizedu.dto.sync.PlanUpdateStatusDTO;
import com.coderman.bizedu.vo.sync.PlanVO;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface PlanService {

    /**
     * 同步计划分页查询
     *
     * @param planPageDTO 查询参数
     * @return
     */
    ResultVO<PageVO<List<PlanVO>>> page(PlanPageDTO planPageDTO);


    /**
     * 查看同步内容
     *
     * @param uuid uuid
     * @return
     */
    ResultVO<PlanVO> selectPlanByUuid(String uuid);


    /**
     * 同步计划更新
     *
     * @param planUpdateDTO 参数对象
     * @return
     */
    ResultVO<Void> update(PlanUpdateDTO planUpdateDTO);


    /**
     * 启用/禁用 同步内容
     *
     * @param planUpdateStatusDTO uuid
     * @return
     */
    ResultVO<Void> updateStatus(PlanUpdateStatusDTO planUpdateStatusDTO);


    /**
     * 同步计划新增
     *
     * @param planSaveDTO 参数对象
     * @return
     */
    ResultVO<Void> save(PlanSaveDTO planSaveDTO);


    /**
     * 同步计划删除
     *
     * @param uuid uuid
     * @return
     */
    ResultVO<Void> delete(String uuid);

    /**
     * 刷新同步计划
     * @return
     */
    ResultVO<Void> refreshSyncPlan();
}
