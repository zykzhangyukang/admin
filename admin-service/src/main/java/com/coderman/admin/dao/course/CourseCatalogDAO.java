package com.coderman.admin.dao.course;

import com.coderman.admin.model.course.CourseCatalogExample;
import com.coderman.admin.model.course.CourseCatalogModel;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface CourseCatalogDAO extends BaseDAO<CourseCatalogModel, CourseCatalogExample> {

    /**
     * 保存课程分类关系
     *
     * @param courseId
     * @param catalogIds
     */
    void insertBatch(@Param(value = "courseId") Integer courseId, @Param(value = "catalogIds") List<Integer> catalogIds);
}