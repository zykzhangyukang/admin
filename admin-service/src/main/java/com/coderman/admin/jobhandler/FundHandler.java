package com.coderman.admin.jobhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.constant.NotificationConstant;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.service.common.FundService;
import com.coderman.admin.service.common.NotificationService;
import com.coderman.admin.utils.FundBean;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JobHandler(value = "fundHandler")
@Component
@Slf4j
public class FundHandler extends IJobHandler{

    @Resource
    private NotificationService notificationService;

    @Resource
    private FundService fundService;

    @Override
    public ReturnT<String> execute(String s) {

        List<FundBean> fundBeans = this.fundService.getListData();

        // 打印信息
        this.printLogInfo(fundBeans);

        NotificationDTO msg = NotificationDTO.builder()
                .title("基金收益提醒")
                .message(JSON.toJSONString(fundBeans))
                .url("/dashboard")
                .type(NotificationConstant.NOTIFICATION_FUND_TIPS)
                .isPop(false).build();
        this.notificationService.sendToTopic(msg);

        return ReturnT.SUCCESS;
    }


    private  void printLogInfo(List<FundBean> fundBeanList) {
        if(CollectionUtils.isEmpty(fundBeanList)){
            return;
        }
        for (FundBean fund : fundBeanList) {
            String msg = String.format("[编码: %s, 基金名称: %s, 估算净值: %s, 估算涨跌: %s, 更新时间: %s, 收益: %s, 今日收益: %s] ",
                    fund.getFundCode(),
                    fund.getFundName(),
                    fund.getGsz(),
                    fund.getGszzl() != null ? (fund.getGszzl().startsWith("-") ? fund.getGszzl() : "+" + fund.getGszzl()) + "%" : "--",
                    fund.getGztime() != null ? fund.getGztime() : "--",
                    fund.getIncome() != null ? fund.getIncome() : "--",
                    fund.getTodayIncome() != null ? fund.getTodayIncome() : "--");
            log.info(msg);
        }
        log.info("================================================================================================================================================");
    }
}
