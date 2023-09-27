package com.coderman.bizedu.dao.log;

import com.coderman.bizedu.model.log.LogExample;
import com.coderman.bizedu.model.log.LogModel;
import com.coderman.bizedu.vo.log.LogVO;
import com.coderman.mybatis.dao.BaseDAO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyukang
 */
public interface LogDAO extends BaseDAO<LogModel, LogExample> {

    /**
     * 分页总条数
     *
     * @param conditionMap
     * @return
     */
    Long countPage(Map<String, Object> conditionMap);

    /**
     * 分页列表
     *
     * @param conditionMap
     * @return
     */
    List<LogVO> page(Map<String, Object> conditionMap);
}