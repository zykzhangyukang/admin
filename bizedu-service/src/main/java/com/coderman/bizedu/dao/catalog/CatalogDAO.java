package com.coderman.bizedu.dao.catalog;

import com.coderman.bizedu.model.catalog.CatalogExample;
import com.coderman.bizedu.model.catalog.CatalogModel;
import com.coderman.bizedu.vo.catalog.CatalogTreeVO;
import com.coderman.bizedu.vo.catalog.CatalogVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询课程分类map
     * @param catalogIds
     * @return
     */
    @MapKey(value = "catalogId")
    Map<Integer, CatalogVO> selectCatalogVoMapByIds(@Param(value = "catalogIds") List<Integer> catalogIds);
}