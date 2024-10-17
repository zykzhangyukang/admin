package com.coderman.admin.jobhandler;

import com.alibaba.fastjson.JSON;
import com.coderman.admin.constant.NotificationConstant;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.service.notification.NotificationService;
import com.coderman.admin.utils.FundBean;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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

@JobHandler(value = "fundRefreshHandler")
@Component
@Slf4j
public class FundRefreshHandler extends IJobHandler {

    @Resource
    private NotificationService notificationService;

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

        List<String> codes = Collections.singletonList("161725,0.8965,24740.72");
        List<String> codeList = new ArrayList<>();
        Map<String, String[]> codeMap = new HashMap<>();

        for (String str : codes) {
            // 兼容原有设置
            String[] strArray = str.contains(",") ? str.split(",") : new String[]{str};
            codeList.add(strArray[0]);
            codeMap.put(strArray[0], strArray);
        }

        List<String> fundBeans = Lists.newArrayList();
        for (String code : codeList) {
            try {
                String url = "http://fundgz.1234567.com.cn/js/" + code + ".js?rt=" + System.currentTimeMillis();
                String result = getRequest(url);
                String json = result.substring(8, result.length() - 2);
                if (!json.isEmpty()) {
                    FundBean bean = JSON.parseObject(json, FundBean.class);
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

                    fundBeans.add(getMessage(bean));

                } else {
                    log.error("Fund编码:[" + code + "]无法获取数据");
                }
            } catch (Exception e) {
                log.error("处理基金编码 [{}] 时发生异常: {}", code, e.getMessage(), e);
            }
        }

        NotificationDTO msg = NotificationDTO.builder()
                .title("基金收益提醒")
                .message(StringUtils.join(fundBeans, ",") + " [刷新时间:" + DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+"]")
                .url("/dashboard")
                .type(NotificationConstant.NOTIFICATION_FUND_TIPS)
                .isPop(false).build();
        this.notificationService.sendToTopic(msg);
        return ReturnT.SUCCESS;
    }


    public static String getMessage(FundBean fund) {
        // log.info(logMessage.toString());
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
