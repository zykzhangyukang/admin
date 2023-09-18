package com.coderman.bizedu.edu.service.catalog.impl;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.auth.utils.TreeUtils;
import com.coderman.bizedu.edu.dao.catalog.CatalogDAO;
import com.coderman.bizedu.edu.service.catalog.CatalogService;
import com.coderman.bizedu.edu.vo.catalog.CatalogTreeVO;
import com.coderman.service.anntation.LogError;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
