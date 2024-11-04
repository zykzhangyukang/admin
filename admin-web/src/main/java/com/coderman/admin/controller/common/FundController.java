package com.coderman.admin.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.service.common.FundService;
import com.coderman.admin.vo.common.FundBeanVO;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2024/10/24 14:32
 */
@Api(value = "基金模块接口", tags = "基金模块接口")
@RestController
@RequestMapping(value = "/trade/fund")
public class FundController {

    @Resource
    private FundService fundService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取基金列表")
    @GetMapping(value = "/list")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"pageRow", "totalRow", "currPage", "totalPage", "dataList"}),
            @ApiReturnParam(name = "FundBeanVO", value = {"gszzl", "costPrise", "incomePercent", "dwjz", "jzrq", "todayIncome", "income", "fundName", "gsz", "fundCode", "bonds", "gztime"}),
    })
    public ResultVO<List<FundBeanVO>> getListData() {
        List<FundBeanVO> list = this.fundService.getListData();
        return ResultUtil.getSuccessList(FundBeanVO.class, list);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "基金搜索")
    @GetMapping(value = "/search")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<List<JSONObject>> getSearchData() throws IOException {
        List<JSONObject> list = this.fundService.getSearchData();
        return ResultUtil.getSuccessList(JSONObject.class, list);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取历史净值")
    @GetMapping(value = "/history")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"pageRow", "totalRow", "currPage", "totalPage", "dataList"}),
    })
    public ResultVO<JSONObject> getHistoryData(Integer currentPage, Integer pageSize, String code) throws IOException {
        JSONObject jsonObject = this.fundService.getHistoryData(currentPage, pageSize, code);
        return ResultUtil.getSuccess(JSONObject.class, jsonObject);
    }
}
