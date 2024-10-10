package com.coderman.admin.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
public class HttpClientExample {

    public static String getRequest(String url) {
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
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {

        List<String> codes = Collections.singletonList("161725,0.9315,10735.02");

        List<String> codeList = new ArrayList<>();
        Map<String, String[]> codeMap = new HashMap<>();
        for (String str : codes) {
            //兼容原有设置
            String[] strArray;
            if (str.contains(",")) {
                strArray = str.split(",");
            } else {
                strArray = new String[]{str};
            }
            codeList.add(strArray[0]);
            codeMap.put(strArray[0], strArray);
        }

        for (String code : codeList) {
            new Thread(() -> {
                try {
                    String url = "http://fundgz.1234567.com.cn/js/" + code + ".js?rt=" + System.currentTimeMillis();
                    String result = getRequest(url);
                    String json = result.substring(8, result.length() - 2);
                    if (!json.isEmpty()) {
                        FundBean bean = JSON.parseObject(json, FundBean.class);
                        FundBean.loadFund(bean, codeMap);

                        BigDecimal now = new BigDecimal(bean.getGsz());
                        String costPriceStr = bean.getCostPrise();
                        if (StringUtils.isNotEmpty(costPriceStr)) {
                            BigDecimal costPriceDec = new BigDecimal(costPriceStr);
                            BigDecimal incomeDiff = now.add(costPriceDec.negate());
                            if (costPriceDec.compareTo(BigDecimal.ZERO) <= 0) {
                                bean.setIncomePercent("0");
                            } else {
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
                            }
                        }

                        log.info(JSON.toJSONString(bean));

                    } else {
                        log.error("Fund编码:[" + code + "]无法获取数据");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
