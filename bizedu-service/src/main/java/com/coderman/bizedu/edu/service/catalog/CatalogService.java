package com.coderman.bizedu.edu.service.catalog;

import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.edu.vo.catalog.CatalogTreeVO;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface CatalogService {

    /**
     * 获取课程分类树
     * @return
     */
    ResultVO<List<CatalogTreeVO>> listTree();

}
