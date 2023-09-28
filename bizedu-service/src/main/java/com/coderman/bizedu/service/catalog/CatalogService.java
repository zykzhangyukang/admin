package com.coderman.bizedu.service.catalog;

import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.dto.catalog.CatalogSaveDTO;
import com.coderman.bizedu.dto.catalog.CatalogUpdateDTO;
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

    /**
     * 新增分类
     * @param catalogSaveDTO
     * @return
     */
    ResultVO<Void> save(CatalogSaveDTO catalogSaveDTO);

    /**
     * 更新分类
     * @param catalogUpdateDTO
     * @return
     */
    ResultVO<Void> update(CatalogUpdateDTO catalogUpdateDTO);

    /**
     * 删除分类
     * @param catalogId 分类id
     * @return
     */
    ResultVO<Void> delete(Integer catalogId);
}
