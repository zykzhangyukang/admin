package com.coderman.admin.controller.resc;

import com.coderman.admin.dto.resc.RescPageDTO;
import com.coderman.admin.dto.resc.RescSaveDTO;
import com.coderman.admin.dto.resc.RescUpdateDTO;
import com.coderman.admin.service.resc.RescService;
import com.coderman.admin.vo.resc.RescVO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.limiter.annotation.RateLimit;
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
 * @author coderman
 * @Title: 资源管理
 * @Description: 资源管理控制器
 * @date 2022/3/19 9:02
 */
@Api(value = "资源管理", tags = "资源管理")
@RestController
@RequestMapping(value = "/auth/resc")
public class RescController {

    @Resource
    private RescService rescService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "资源列表")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "pageRow", "totalRow", "currPage", "totalPage"}),
            @ApiReturnParam(name = "RescVO", value = {"rescId", "rescName", "rescUrl", "rescDomain", "createTime", "updateTime", "methodType"})
    })
    public ResultVO<PageVO<List<RescVO>>> page(@RequestBody RescPageDTO rescPageDTO) {
        return this.rescService.page(rescPageDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "列表导出")
    @PostMapping(value = "/export")
    public void export(@RequestBody RescPageDTO rescPageDTO) {
        this.rescService.export(rescPageDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "新增资源")
    @PostMapping(value = "/save")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> save(@RequestBody RescSaveDTO rescSaveDTO) {
        return this.rescService.save(rescSaveDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "更新资源")
    @PutMapping(value = "/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> update(@RequestBody RescUpdateDTO rescUpdateDTO) {
        return this.rescService.update(rescUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "搜索资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", paramType = SwaggerConstant.PARAM_FORM, dataType = SwaggerConstant.DATA_STRING, value = "关键字")
    })
    @GetMapping(value = "/search")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "RescVO", value = {"rescName", "rescUrl", "rescDomain", "createTime", "updateTime", "methodType"})
    })
    public ResultVO<List<RescVO>> search(@RequestParam(value = "keyword") String keyword) {
        return this.rescService.search(keyword);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "刷新系统资源")
    @PutMapping(value = "/refresh")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    @RateLimit
    public ResultVO<Void> refresh() {
        return this.rescService.refresh();
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_DELETE, value = "删除资源")
    @DeleteMapping(value = "/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rescId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "资源Id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> delete(@RequestParam(value = "rescId") Integer rescId) {
        return this.rescService.delete(rescId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取资源")
    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rescId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "资源Id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "RescVO", value = {"rescUrl", "rescName", "rescId", "rescDomain", "createTime", "updateTime", "methodType"})
    })
    public ResultVO<RescVO> select(@RequestParam(value = "rescId") Integer rescId) {
        return this.rescService.selectById(rescId);
    }
}
