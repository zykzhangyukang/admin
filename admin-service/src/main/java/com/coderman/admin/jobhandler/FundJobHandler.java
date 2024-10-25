package com.coderman.admin.jobhandler;

import com.alibaba.fastjson.JSON;
import com.coderman.admin.constant.NotificationConstant;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.service.common.FundService;
import com.coderman.admin.service.common.NotificationService;
import com.coderman.admin.utils.FundBean;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class FundJobHandler {

    @Resource
    private NotificationService notificationService;
    @Resource
    private FundService fundService;
    @Resource
    private RedisService redisService;

    /**
     * 刷新基金实时走势
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void refreshFundData() {

        List<FundBean> fundBeans = this.fundService.getListData();
        if (CollectionUtils.isEmpty(fundBeans)) {
            return;
        }

        // 保存到redis
        this.saveToRedis(fundBeans);

        // 打印日志
        this.printLog(fundBeans);

        NotificationDTO msg = NotificationDTO.builder()
                .title("基金收益提醒")
                .message(JSON.toJSONString(fundBeans))
                .url("/dashboard")
                .type(NotificationConstant.NOTIFICATION_FUND_TIPS)
                .isPop(false).build();
        this.notificationService.sendToTopic(msg);
    }

    private void saveToRedis(List<FundBean> fundBeans) {
        if (!isOpen(LocalDateTime.now())) {
            return;
        }

        for (FundBean fund : fundBeans) {

            String redisKey = "TIME_SERIES_KEY_" + fund.getFundCode() + ":" + DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            // 将字符串时间转换为时间戳
            long timestamp = convertToTimestamp(fund.getGztime());

            this.redisService.zSetAdd(redisKey, fund , timestamp,RedisDbConstant.REDIS_DB_DEFAULT);
        }
    }

    private void printLog(List<FundBean> fundBeans) {
        for (FundBean fund : fundBeans) {
            String msg = String.format(
                    "[编码: %s, 基金名称: %s, 估算净值: %s, 估算涨跌: %s, 更新时间: %s, 收益率: %s, 收益: %s, 今日收益: %s] ",
                    fund.getFundCode(),
                    fund.getFundName(),
                    fund.getGsz(),
                    fund.getGszzl() != null ? (fund.getGszzl().startsWith("-") ? fund.getGszzl() : "+" + fund.getGszzl()) + "%" : "--",
                    fund.getGztime() != null ? fund.getGztime() : "--",
                    fund.getIncomePercent() != null ? fund.getIncomePercent() + "%" : "--",
                    fund.getIncome() != null ? fund.getIncome() : "--",
                    fund.getTodayIncome() != null ? fund.getTodayIncome() : "--"
            );
            log.info(msg);
        }
        log.info("=========================================================================================================================================================");
    }

    private long convertToTimestamp(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(timeString);
            return date.getTime() / 1000; // 转换为秒级时间戳
        } catch (ParseException e) {
            throw new RuntimeException("时间格式不正确：" + timeString, e);
        }
    }

    public static boolean isOpen(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();

        // 判断是否在工作日（周一到周五）
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            // 判断上午 9:30 到 11:30
            if (time.isAfter(LocalTime.of(9, 30)) && time.isBefore(LocalTime.of(11, 30))) {
                return true;
            }
            // 判断下午 13:00 到 15:00
            return time.isAfter(LocalTime.of(13, 0)) && time.isBefore(LocalTime.of(15, 0));
        }
        return false;
    }
}
