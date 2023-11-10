package com.coderman.admin.dao.log;

import com.coderman.admin.model.log.LogExample;
import com.coderman.admin.model.log.LogModel;
import com.coderman.admin.vo.log.LogVO;
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