package com.coderman.admin.controller.func;

import com.coderman.admin.vo.resc.RescVO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dto.func.FuncPageDTO;
import com.coderman.admin.dto.func.FuncRescUpdateDTO;
import com.coderman.admin.dto.func.FuncSaveDTO;
import com.coderman.admin.dto.func.FuncUpdateDTO;
import com.coderman.admin.service.func.FuncService;
import com.coderman.admin.vo.func.FuncTreeVO;
import com.coderman.admin.vo.func.FuncVO;
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
 * 功能管理控制器
 * 负责功能的增删改查及功能树的获取等操作。
 *
 * @author coderman
 * @date 2022/03/19
 */
@Api(value = "功能管理", tags = "功能管理")
@RestController
@RequestMapping("/auth/func")
public class FuncController {

    @Resource
    private FuncService funcService;

    @ApiOperation(value = "获取功能树", httpMethod = SwaggerConstant.METHOD_GET)
    @GetMapping("/tree")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "FuncVO", value = {"funcTreeVOList", "funcVOList"}),
            @ApiReturnParam(name = "FuncTreeVO", value = {"funcName", "funcKey", "createTime", "updateTime", "children", "funcId", "parentId", "funcSort", "funcType"})
    })
    public ResultVO<List<FuncTreeVO>> listTree() {
        return funcService.listTree();
    }

    @ApiOperation(value = "功能解绑用户", httpMethod = SwaggerConstant.METHOD_DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "funcId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "功能ID", required = true)
    })
    @PutMapping("/user/remove")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> deleteUserBind(@RequestParam Integer funcId) {
        return funcService.deleteUserBind(funcId);
    }

    @ApiOperation(value = "功能解绑资源", httpMethod = SwaggerConstant.METHOD_DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "funcId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "功能ID", required = true)
    })
    @DeleteMapping("/resc/remove")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> deleteResourceBind(@RequestParam Integer funcId) {
        return funcService.deleteResourceBind(funcId);
    }

    @ApiOperation(value = "功能绑定资源", httpMethod = SwaggerConstant.METHOD_PUT)
    @PutMapping("/resc/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> updateFuncResc(@RequestBody FuncRescUpdateDTO funcRescUpdateDTO) {
        return funcService.updateFuncResc(funcRescUpdateDTO);
    }

    @ApiOperation(value = "功能列表", httpMethod = SwaggerConstant.METHOD_POST)
    @PostMapping("/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "pageRow", "totalRow", "currPage", "totalPage"}),
            @ApiReturnParam(name = "FuncVO", value = {"funcSort", "hide", "userVOList", "rescVOList", "funcName", "funcKey", "createTime", "funcType", "updateTime", "children", "funcId", "parentId", "rescIdList"})
    })
    public ResultVO<PageVO<List<FuncVO>>> page(@RequestBody FuncPageDTO funcPageDTO) {
        return funcService.page(funcPageDTO);
    }

    @ApiOperation(value = "保存功能", httpMethod = SwaggerConstant.METHOD_POST)
    @PostMapping("/save")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> save(@RequestBody FuncSaveDTO funcSaveDTO) {
        return funcService.save(funcSaveDTO);
    }

    @ApiOperation(value = "更新功能", httpMethod = SwaggerConstant.METHOD_PUT)
    @PutMapping("/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> update(@RequestBody FuncUpdateDTO funcUpdateDTO) {
        return funcService.update(funcUpdateDTO);
    }

    @ApiOperation(value = "删除功能", httpMethod = SwaggerConstant.METHOD_DELETE)
    @DeleteMapping("/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "funcId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "功能ID", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> delete(@RequestParam Integer funcId) {
        return funcService.delete(funcId);
    }

    @ApiOperation(value = "获取功能详情", httpMethod = SwaggerConstant.METHOD_GET)
    @GetMapping("/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "funcId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "功能ID", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "FuncVO", value = {"funcSort", "hide", "userVOList", "rescVOList", "rescIdList", "funcName", "funcKey", "createTime", "updateTime", "childrenList", "funcId", "parentId", "funcType"})
    })
    public ResultVO<FuncVO> selectById(@RequestParam Integer funcId) {
        return funcService.selectById(funcId);
    }
}
