package com.coderman.bizedu.service.catalog.impl;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.dto.catalog.CatalogSaveDTO;
import com.coderman.bizedu.dto.catalog.CatalogUpdateDTO;
import com.coderman.bizedu.model.catalog.CatalogExample;
import com.coderman.bizedu.model.catalog.CatalogModel;
import com.coderman.bizedu.utils.TreeUtils;
import com.coderman.bizedu.dao.catalog.CatalogDAO;
import com.coderman.bizedu.service.catalog.CatalogService;
import com.coderman.bizedu.vo.catalog.CatalogTreeVO;
import com.coderman.bizedu.vo.catalog.CatalogVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
        if (CollectionUtils.isEmpty(catalogIds)) {
            return Maps.newHashMap();
        }
        return this.catalogDAO.selectCatalogVoMapByIds(catalogIds);
    }

    @Override
    @LogError(value = "新增分类")
    public ResultVO<Void> save(@LogErrorParam CatalogSaveDTO catalogSaveDTO) {

        Integer parentId = Optional.ofNullable(catalogSaveDTO.getParentId()).orElse(0);
        String catalogName = catalogSaveDTO.getCatalogName();

        if (StringUtils.isBlank(catalogName)) {
            return ResultUtil.getWarn("课程分类不能为空！");
        }
        if (StringUtils.length(catalogName) > 30) {
            return ResultUtil.getWarn("课程分类最多30个字符！");
        }

        CatalogModel catalogModel = new CatalogModel();
        catalogModel.setCatalogName(catalogName);
        catalogModel.setCreateTime(new Date());
        catalogModel.setParentId(parentId);
        catalogModel.setUpdateTime(new Date());
        this.catalogDAO.insertSelective(catalogModel);
        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "更新分类")
    public ResultVO<Void> update(@LogErrorParam CatalogUpdateDTO catalogUpdateDTO) {

        Integer catalogId = catalogUpdateDTO.getCatalogId();
        String catalogName = catalogUpdateDTO.getCatalogName();

        if (Objects.isNull(catalogId)) {
            return ResultUtil.getWarn("分类id不能为空！");
        }
        CatalogModel dbCatalogModel = this.catalogDAO.selectByPrimaryKey(catalogId);
        if (Objects.isNull(dbCatalogModel)) {
            return ResultUtil.getWarn("课程分类不存在！");
        }
        if (StringUtils.length(catalogName) > 30) {
            return ResultUtil.getWarn("课程分类最多30个字符！");
        }
        if (StringUtils.isBlank(catalogName)) {
            return ResultUtil.getWarn("课程分类不能为空！");
        }

        CatalogModel catalogModel = new CatalogModel();
        catalogModel.setCatalogId(catalogId);
        catalogModel.setCatalogName(catalogName);
        catalogModel.setUpdateTime(new Date());
        this.catalogDAO.updateByPrimaryKeySelective(catalogModel);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "删除分类")
    public ResultVO<Void> delete(Integer catalogId) {

        if (Objects.isNull(catalogId)) {
            return ResultUtil.getWarn("分类id不能为空！");
        }

        CatalogModel dbCatalogModel = this.catalogDAO.selectByPrimaryKey(catalogId);
        if (Objects.isNull(dbCatalogModel)) {
            return ResultUtil.getWarn("课程分类不存在！");
        }

        // 判断一下是否有子分类
        CatalogExample example = new CatalogExample();
        example.createCriteria().andParentIdEqualTo(dbCatalogModel.getCatalogId());
        Long count = this.catalogDAO.countByExample(example);
        if (count > 0) {
            return ResultUtil.getWarn("当前分类存在子分类！");
        }

        this.catalogDAO.deleteByPrimaryKey(catalogId);

        return ResultUtil.getSuccess();
    }
}
