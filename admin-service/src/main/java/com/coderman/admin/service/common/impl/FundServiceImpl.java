package com.coderman.admin.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.service.common.FundService;
import com.coderman.admin.utils.FundBean;
import com.coderman.service.anntation.LogError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author ：zhangyukang
 * @date ：2024/10/24 14:38
 */
@Service
@Slf4j
public class FundServiceImpl implements FundService {

    @Override
    @LogError(value = "基金列表")
    public List<FundBean> getListData() {

        List<String> codes = Lists.newArrayList();

        codes.add("161725,0.8965,24740.72");
        codes.add("008888,1.0340,2417.79");
        codes.add("007690,1.5868,1575.50");

        List<String> codeList = new ArrayList<>();
        Map<String, String[]> codeMap = new HashMap<>();

        for (String str : codes) {
            // 兼容原有设置
            String[] strArray = str.contains(",") ? str.split(",") : new String[]{str};
            codeList.add(strArray[0]);
            codeMap.put(strArray[0], strArray);
        }

        List<FundBean> fundBeans = Lists.newArrayList();
        for (String code : codeList) {
            try {
                fundBeans.add(this.fetchListData(code, codeMap));
            } catch (Exception e) {
                log.error("处理基金编码 [{}] 时发生异常: {}", code, e.getMessage(), e);
            }
        }
        return fundBeans;
    }

    @Override
    @LogError(value = "获取历史净值")
    public JSONObject getHistoryData(Integer currentPage, Integer pageSize, String code) {

        currentPage = Optional.ofNullable(currentPage).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(20);

        String url = "https://api.fund.eastmoney.com/f10/lsjz?callback=jQuery18309019060760859061_1729219122448&fundCode=" + code +
                "&pageIndex="+currentPage+"&pageSize="+pageSize+"&startDate=&endDate=&_=" + System.currentTimeMillis();

        String jsonpResponse = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);
            // 设置请求头
            request.setHeader("accept", "*/*");
            request.setHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
            request.setHeader("cache-control", "no-cache");
            request.setHeader("pragma", "no-cache");
            request.setHeader("sec-ch-ua", "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"");
            request.setHeader("sec-ch-ua-mobile", "?0");
            request.setHeader("sec-ch-ua-platform", "\"Windows\"");
            request.setHeader("sec-fetch-dest", "script");
            request.setHeader("sec-fetch-mode", "no-cors");
            request.setHeader("sec-fetch-site", "same-site");
            request.setHeader("referer", "https://fundf10.eastmoney.com/");
            request.setHeader("referrerPolicy", "strict-origin-when-cross-origin");

            // 执行请求
            HttpResponse response = httpClient.execute(request);
            jsonpResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            log.error("HTTP请求失败: {}", e.getMessage());
        }

        Assert.notNull(jsonpResponse , "获取数据错误!");

        int startIndex = jsonpResponse.indexOf('(') + 1;
        int endIndex = jsonpResponse.lastIndexOf(')');

        String jsonResponse = jsonpResponse.substring(startIndex, endIndex);
        JSONObject jsonObject = JSON.parseObject(jsonResponse);
        return jsonObject.getJSONObject("Data");
    }


    private FundBean fetchListData(String code, Map<String, String[]> codeMap) {

        FundBean bean = null;

        String url = "http://fundgz.1234567.com.cn/js/" + code + ".js?rt=" + System.currentTimeMillis();
        String result = null;
        // 创建一个HttpClient实例
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // 创建一个GET请求
            HttpGet request = new HttpGet(url);
            // 执行请求
            HttpResponse response = httpClient.execute(request);
            // 获取响应内容
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            log.error("HTTP请求失败: {}", e.getMessage());
        }
        Assert.notNull(result, "获取数据错误!");

        String json = result.substring(8, result.length() - 2);
        if (!json.isEmpty()) {
            bean = JSON.parseObject(json, FundBean.class);
            FundBean.loadFund(bean, codeMap);

            // 当前基金净值估算
            BigDecimal now = new BigDecimal(bean.getGsz());
            // 持仓成本价
            String costPriceStr = bean.getCostPrise();

            if (StringUtils.isNotEmpty(costPriceStr)) {
                BigDecimal costPriceDec = new BigDecimal(costPriceStr);
                BigDecimal incomeDiff = now.add(costPriceDec.negate());
                if (costPriceDec.compareTo(BigDecimal.ZERO) <= 0) {
                    bean.setIncomePercent("0");
                } else {
                    // 计算收益率 =
                    BigDecimal incomePercentDec = incomeDiff.divide(costPriceDec, 8, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.TEN)
                            .multiply(BigDecimal.TEN)
                            .setScale(3, RoundingMode.HALF_UP);
                    bean.setIncomePercent(incomePercentDec.toString());
                }

                String bondStr = bean.getBonds();
                if (StringUtils.isNotEmpty(bondStr)) {
                    BigDecimal bondDec = new BigDecimal(bondStr);
                    BigDecimal incomeDec = incomeDiff.multiply(bondDec)
                            .setScale(2, RoundingMode.HALF_UP);
                    bean.setIncome(incomeDec.toString());

                    // 计算当天收益  = (当前净值 - 昨天净值) * 份额
                    if (bean.getDwjz() != null) {
                        // 计算当天收益
                        BigDecimal decimal = new BigDecimal(bean.getDwjz());
                        BigDecimal currentEarnings = now.subtract(decimal).multiply(bondDec);
                        bean.setTodayIncome(currentEarnings.setScale(2, RoundingMode.HALF_UP).toString());
                    }
                }
            }

        } else {
            log.error("Fund编码:[" + code + "]无法获取数据");
        }

        return bean;
    }
}
