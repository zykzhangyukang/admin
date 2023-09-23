package com.coderman.bizedu.service.catalog.impl;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.utils.TreeUtils;
import com.coderman.bizedu.dao.catalog.CatalogDAO;
import com.coderman.bizedu.service.catalog.CatalogService;
import com.coderman.bizedu.vo.catalog.CatalogTreeVO;
import com.coderman.bizedu.vo.catalog.CatalogVO;
import com.coderman.service.anntation.LogError;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 17:39
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Resource
    private CatalogDAO catalogDAO;

    @Override
    @LogError(value = "获取课程分类树")
    public ResultVO<List<CatalogTreeVO>> listTree() {
        List<CatalogTreeVO> catalogVOList = this.catalogDAO.selectAllCatalogTreeVO();
        List<CatalogTreeVO> treeList = TreeUtils.buildCatalogTreeByList(catalogVOList);
        return ResultUtil.getSuccessList(CatalogTreeVO.class, treeList);
    }

    @Override
    @LogError(value = "查询课程分类map")
    public Map<Integer, CatalogVO> selectCatalogVoMapByIds(List<Integer> catalogIds) {
        if(CollectionUtils.isEmpty(catalogIds)){
            return Maps.newHashMap();
        }
        return this.catalogDAO.selectCatalogVoMapByIds(catalogIds);
    }
}
