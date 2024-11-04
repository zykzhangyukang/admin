package com.coderman.admin.service.common;

import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.vo.common.FundBeanVO;

import java.io.IOException;
import java.util.List;

public interface FundService {

    /**
     * 基金列表
     * @return
     */
    List<FundBeanVO> getListData();
    /**
     * 获取历史净值
     * @param currentPage
     * @param pageSize
     * @param code
     * @return
     */
    JSONObject getHistoryData(Integer currentPage, Integer pageSize, String code) throws IOException;
    /**
     * 基金搜索
     *
     * @return
     */
    List<JSONObject> getSearchData() throws IOException;

}
