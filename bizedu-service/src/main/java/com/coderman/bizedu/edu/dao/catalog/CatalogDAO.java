package com.coderman.bizedu.edu.dao.catalog;

import com.coderman.bizedu.edu.model.catalog.CatalogExample;
import com.coderman.bizedu.edu.model.catalog.CatalogModel;
import com.coderman.bizedu.edu.vo.catalog.CatalogTreeVO;
import com.coderman.mybatis.dao.BaseDAO;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface CatalogDAO extends BaseDAO<CatalogModel, CatalogExample> {

    /**
     * 查询所有课程分类
     *
     * @return
     */
    List<CatalogTreeVO> selectAllCatalogTreeVO();

}