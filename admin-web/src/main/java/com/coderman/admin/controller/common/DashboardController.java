package com.coderman.admin.controller.common;

import com.alibaba.fastjson.JSONArray;
import com.coderman.admin.spider.BaiduHotSpider;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ：zhangyukang
 * @date ：2024/10/12 15:27
 */
@Api(value = "控制台", tags = "控制台")
@RestController
public class DashboardController {

    @Resource
    private BaiduHotSpider baiduHotSpider;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "分片上传开始")
    @GetMapping(value = "/dashboard")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Map<String, Object>> dashboard(@RequestParam(value = "order", defaultValue = "desc") String order,
                                                                                        @RequestParam(value = "top", defaultValue = "10") Integer top) {

        Map<String, Object> map = Maps.newHashMap();

        // 百度热搜
        JSONArray hotsearch = this.baiduHotSpider.getTopRecords(order, top);
        map.put("hotsearch", hotsearch);

        return ResultUtil.getSuccessMap(Map.class, map);
    }

}
