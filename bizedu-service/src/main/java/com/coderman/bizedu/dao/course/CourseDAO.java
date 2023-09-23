package com.coderman.bizedu.dao.course;

import com.coderman.bizedu.model.course.CourseExample;
import com.coderman.bizedu.model.course.CourseModel;
import com.coderman.bizedu.vo.course.CourseVO;
import com.coderman.mybatis.dao.BaseDAO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyukang
 */
public interface CourseDAO extends BaseDAO<CourseModel, CourseExample> {

    /**
     * 分页条数
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
    List<CourseVO> selectPage(Map<String, Object> conditionMap);

    /**
     * 插入并返回主键
     *
     * @param record
     * @return
     */
    int insertSelectiveReturnKey(CourseModel record);
}