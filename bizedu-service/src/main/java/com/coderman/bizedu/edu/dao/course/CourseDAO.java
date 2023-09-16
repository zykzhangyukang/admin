package com.coderman.bizedu.edu.dao.course;

import com.coderman.bizedu.edu.model.course.CourseExample;
import com.coderman.bizedu.edu.model.course.CourseModel;
import com.coderman.bizedu.edu.vo.course.CourseVO;
import com.coderman.mybatis.dao.BaseDAO;

import java.util.List;
import java.util.Map;

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
}