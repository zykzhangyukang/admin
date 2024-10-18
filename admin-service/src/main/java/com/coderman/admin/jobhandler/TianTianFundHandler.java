package com.coderman.admin.jobhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.constant.NotificationConstant;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.service.notification.NotificationService;
import com.coderman.admin.utils.FundBean;
import com.google.common.collect.Maps;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@JobHandler(value = "tianTianFundRefreshHandler")
@Component
@Slf4j
public class TianTianFundHandler extends IJobHandler {

    @Resource
    private NotificationService notificationService;

    private static final int SHORT_TERM_PERIOD = 5; // 短期移动平均线周期
    private static final int LONG_TERM_PERIOD = 20; // 长期移动平均线周期
    private static final BigDecimal RSI_OVERBOUGHT = new BigDecimal("70"); // RSI超买阈值
    private static final BigDecimal RSI_OVERSOLD = new BigDecimal("30"); // RSI超卖阈值

    public static String getHistoryRequest(String url) {
        String result = null;
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
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            log.error("HTTP请求失败: {}", e.getMessage());
        }
        return result;
    }

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
            log.error("HTTP请求失败: {}", e.getMessage());
        }
        return result;
    }

    @Override
    public ReturnT<String> execute(String s) {

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

                // 获取当前的净值情况
                FundBean fundBean = this.fetchData(code, codeMap);
                fundBeans.add(fundBean);

                // 获取历史的净值情况
                this.fetchHistoryData(code);

            } catch (Exception e) {
                log.error("处理基金编码 [{}] 时发生异常: {}", code, e.getMessage(), e);
            }
        }

        NotificationDTO msg = NotificationDTO.builder()
                .title("基金收益提醒")
                .message(JSON.toJSONString(fundBeans))
                .url("/dashboard")
                .type(NotificationConstant.NOTIFICATION_FUND_TIPS)
                .isPop(false).build();
        this.notificationService.sendToTopic(msg);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return ReturnT.SUCCESS;
    }

    private FundBean fetchData(String code, Map<String, String[]> codeMap) {

        FundBean bean = null;

        String url = "http://fundgz.1234567.com.cn/js/" + code + ".js?rt=" + System.currentTimeMillis();
        String result = getRequest(url);
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

            log.info(getMessage(bean));

        } else {
            log.error("Fund编码:[" + code + "]无法获取数据");
        }

        return bean;
    }

    private void fetchHistoryData(String code) {

        String url = "https://api.fund.eastmoney.com/f10/lsjz?callback=jQuery18309019060760859061_1729219122448&fundCode=" + code +
                "&pageIndex=1&pageSize=20&startDate=&endDate=&_=" + System.currentTimeMillis();
        String jsonpResponse = getHistoryRequest(url);

        int startIndex = jsonpResponse.indexOf('(') + 1;
        int endIndex = jsonpResponse.lastIndexOf(')');

        String jsonResponse = jsonpResponse.substring(startIndex, endIndex);
        JSONObject jsonObject = JSON.parseObject(jsonResponse);
        JSONObject data = jsonObject.getJSONObject("Data");

        // 第四步：获取 LSJZList 数组
        JSONArray historyList = data.getJSONArray("LSJZList");
        for (Object o : historyList) {
            JSONObject object = (JSONObject) o;
            String fsrq = object.getString("FSRQ");
            BigDecimal dwjz = object.getBigDecimal("DWJZ");
            log.info("{}=>{}", fsrq, dwjz);
        }

    }


    public static String getMessage(FundBean fund) {
        return "基金信息: " + String.format("[编码: %s, 基金名称: %s, 估算净值: %s, 估算涨跌: %s, 更新时间: %s, 收益: %s, 今日收益: %s] ",
                fund.getFundCode(),
                fund.getFundName(),
                fund.getGsz(),
                fund.getGszzl() != null ? (fund.getGszzl().startsWith("-") ? fund.getGszzl() : "+" + fund.getGszzl()) + "%" : "--",
                fund.getGztime() != null ? fund.getGztime() : "--",
                fund.getIncome() != null ? fund.getIncome() : "--",
                fund.getTodayIncome() != null ? fund.getTodayIncome() : "--"
        );
    }
}
