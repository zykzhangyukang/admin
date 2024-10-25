package com.coderman.admin.jobhandler;

import com.alibaba.fastjson.JSON;
import com.coderman.admin.constant.NotificationConstant;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.service.common.FundService;
import com.coderman.admin.service.common.NotificationService;
import com.coderman.admin.utils.FundBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
@Slf4j
public class FundHandler {

    @Resource
    private NotificationService notificationService;

    @Resource
    private FundService fundService;

    /**
     * 刷新基金实时走势
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void refreshFundData() {

        List<FundBean> fundBeans = this.fundService.getListData();
        if (CollectionUtils.isEmpty(fundBeans)) {
            return;
        }

        for (FundBean fund : fundBeans) {
            String msg = String.format("[编码: %s, 基金名称: %s, 估算净值: %s, 估算涨跌: %s, 更新时间: %s, 收益率: %s, 收益: %s, 今日收益: %s] ",
                    fund.getFundCode(),
                    fund.getFundName(),
                    fund.getGsz(),
                    fund.getGszzl() != null ? (fund.getGszzl().startsWith("-") ? fund.getGszzl() : "+" + fund.getGszzl()) + "%" : "--",
                    fund.getGztime() != null ? fund.getGztime() : "--",
                    fund.getIncomePercent() != null ? fund.getIncomePercent() + "%" : "--",
                    fund.getIncome() != null ? fund.getIncome() : "--",
                    fund.getTodayIncome() != null ? fund.getTodayIncome() : "--");
            log.info(msg);
        }
        NotificationDTO msg = NotificationDTO.builder()
                .title("基金收益提醒")
                .message(JSON.toJSONString(fundBeans))
                .url("/dashboard")
                .type(NotificationConstant.NOTIFICATION_FUND_TIPS)
                .isPop(false).build();
        this.notificationService.sendToTopic(msg);

        log.info("=========================================================================================================================================================");
    }
}
