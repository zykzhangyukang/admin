package com.coderman.bizedu.dao.chapter;

import com.coderman.bizedu.model.chapter.ChapterExample;
import com.coderman.bizedu.model.chapter.ChapterModel;
import com.coderman.bizedu.vo.chapter.ChapterVO;
import com.coderman.mybatis.dao.BaseDAO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyukang
 */
public interface ChapterDAO extends BaseDAO<ChapterModel, ChapterExample> {

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
    List<ChapterVO> selectPage(Map<String, Object> conditionMap);
}