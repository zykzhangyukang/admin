package com.coderman.bizedu.service.catalog;

import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.vo.catalog.CatalogTreeVO;
import com.coderman.bizedu.vo.catalog.CatalogVO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyukang
 */
public interface CatalogService {

    /**
     * 获取课程分类树
     *
     * @return
     */
    ResultVO<List<CatalogTreeVO>> listTree();

    /**
     * 查询课程分类map
     *
     * @param catalogIds
     * @return
     */
    Map<Integer, CatalogVO> selectCatalogVoMapByIds(List<Integer> catalogIds);
}
