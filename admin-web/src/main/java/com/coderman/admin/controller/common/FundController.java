package com.coderman.admin.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.service.common.FundService;
import com.coderman.admin.vo.common.FundBeanVO;
import com.coderman.admin.vo.common.FundSettingItemVO;
import com.coderman.admin.vo.common.MarketIndexVO;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2024/10/24 14:32
 */
@Api(value = "理财模块接口", tags = "理财模块接口")
@RestController
@RequestMapping(value = "/trade")
public class FundController {

    @Resource
    private FundService fundService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取基金列表")
    @GetMapping(value = "/fund/list")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"pageRow", "totalRow", "currPage", "totalPage", "dataList"}),
            @ApiReturnParam(name = "FundBeanVO", value = {"gszzl", "costPrise", "incomePercent", "dwjz", "jzrq",
                    "todayIncome", "income", "fundName", "gsz", "fundCode", "bonds", "gztime","jz5","jz10","jz20","jz30"}),
    })
    public ResultVO<List<FundBeanVO>> getListData() {
        List<FundBeanVO> list = this.fundService.getListData();
        return ResultUtil.getSuccessList(FundBeanVO.class, list);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取大盘信息")
    @GetMapping(value = "/mark/index/info")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "MarketIndexVO", value = {"indexName", "changeRate","changeVal", "index"}),
    })
    public ResultVO<List<MarketIndexVO>> getMarkIndexList() throws IOException {
        List<MarketIndexVO> markIndexList = this.fundService.getMarkIndexList();
        return ResultUtil.getSuccessList(MarketIndexVO.class, markIndexList);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "基金搜索")
    @GetMapping(value = "/fund/search")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<List<JSONObject>> getSearchData() throws IOException {
        List<JSONObject> list = this.fundService.getSearchData();
        return ResultUtil.getSuccessList(JSONObject.class, list);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "保存基金设置")
    @PostMapping(value = "/fund/save/setting")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Void> saveSetting(@RequestBody List<FundSettingItemVO> settingItemVos) {
        return this.fundService.saveSetting(settingItemVos);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "保存基金设置")
    @GetMapping(value = "/fund/get/setting")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "FundSettingItemVO", value = {"costPrise", "fundCode", "bonds"}),
    })
    public ResultVO<List<FundSettingItemVO>> getSetting() {
        return this.fundService.getSetting();
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "导出基金设置")
    @PostMapping(value = "/fund/export/setting")
    public void exportSetting(HttpServletResponse response) throws IOException {
        this.fundService.exportSetting(response);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "导入基金设置")
    @PostMapping(value = "/fund/import/setting")
    public ResultVO<Void> importSetting(@RequestPart("file") MultipartFile file) throws IOException {
        return this.fundService.importSetting(file);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取历史净值")
    @GetMapping(value = "/fund/history")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"pageRow", "totalRow", "currPage", "totalPage", "dataList"}),
    })
    public ResultVO<JSONObject> getHistoryData(Integer currentPage, Integer pageSize, String code) throws IOException {
        JSONObject jsonObject = this.fundService.getHistoryData(currentPage, pageSize, code);
        return ResultUtil.getSuccess(JSONObject.class, jsonObject);
    }
}
