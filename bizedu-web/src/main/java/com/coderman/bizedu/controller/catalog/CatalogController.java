package com.coderman.bizedu.controller.catalog;

import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.dto.catalog.CatalogSaveDTO;
import com.coderman.bizedu.dto.catalog.CatalogUpdateDTO;
import com.coderman.bizedu.dto.func.FuncUpdateDTO;
import com.coderman.bizedu.service.catalog.CatalogService;
import com.coderman.bizedu.vo.catalog.CatalogTreeVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 17:32
 */
@Api(value = "课程分类管理", tags = "课程分类")
@RestController
@RequestMapping(value = "/edu/catalog")
public class CatalogController {

    @Resource
    private CatalogService catalogService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取课程分类树")
    @GetMapping(value = "/tree")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "CatalogTreeVO", value = {"catalogId", "updateTime", "catalogName", "createTime", "parentId", "children"}),
    })
    public ResultVO<List<CatalogTreeVO>> listTree() {
        return this.catalogService.listTree();
    }

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "新增分类")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> save(@RequestBody CatalogSaveDTO catalogSaveDTO) {
        return this.catalogService.save(catalogSaveDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "更新分类")
    @PutMapping(value = "/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> update(@RequestBody CatalogUpdateDTO catalogUpdateDTO) {
        return this.catalogService.update(catalogUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_DELETE, value = "删除分类")
    @DeleteMapping(value = "/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "catalogId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "分类di", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> delete(@RequestParam(value = "catalogId") Integer catalogId) {
        return this.catalogService.delete(catalogId);
    }

}
