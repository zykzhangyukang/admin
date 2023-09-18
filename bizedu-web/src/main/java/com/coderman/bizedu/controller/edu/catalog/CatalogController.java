package com.coderman.bizedu.controller.edu.catalog;

import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.edu.service.catalog.CatalogService;
import com.coderman.bizedu.edu.vo.catalog.CatalogTreeVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 17:32
 */
@Api(value = "课程分类管理", tags = "课程分类管理")
@RestController
@RequestMapping(value = "/edu/catalog")
public class CatalogController {

    @Resource
    private CatalogService catalogService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取课程分类树")
    @GetMapping(value = "/tree")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<List<CatalogTreeVO>> listTree() {
        return this.catalogService.listTree();
    }


}
