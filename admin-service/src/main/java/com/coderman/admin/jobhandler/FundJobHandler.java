package com.coderman.admin.jobhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.coderman.admin.constant.NotificationConstant;
import com.coderman.admin.dto.common.NotificationDTO;
import com.coderman.admin.service.common.FundService;
import com.coderman.admin.service.common.NotificationService;
import com.coderman.admin.utils.WxApiUtils;
import com.coderman.admin.vo.common.FundBeanVO;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;


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
     * (每天上午11:40和下午14:40执行)
     * 基金收益提醒
     */
     @Scheduled(cron = "0 40 10,14 * * ?")
    public void notifyFundDataToUser() {
        if(!isOpen(LocalDateTime.now())){
            return;
        }
        List<FundBeanVO> currentFundData = this.getRedisData();
        if (CollectionUtils.isEmpty(currentFundData)) {
            return;
        }
        StringBuilder message = new StringBuilder();
        int size = currentFundData.size();
        for (int i = 0; i < size; i++) {
            FundBeanVO fundBeanVO = currentFundData.get(i);
            message
                    .append(fundBeanVO.getFundName()).append("(").append(fundBeanVO.getFundCode()).append(")")
                    .append(", 估算涨跌: ")
                    .append(fundBeanVO.getGszzl()).append("%")
                    .append(", 当日净值: ")
                    .append(fundBeanVO.getDwjz())
                    .append(", 当前净值: ")
                    .append(fundBeanVO.getGsz())
                    .append(", 今日收益: ")
                    .append(fundBeanVO.getTodayIncome());
            if (i < size - 1) {
                message.append("\n");
            }
        }

        // 发送企业微信消息
        JSONObject jsonObject = WxApiUtils.getInstance().sendMessage(Collections.singletonList("zhangyukang"), message.toString());
        log.info("发送企业微信消息:{}", jsonObject.toJSONString());

        // 发送系统消息提醒
         NotificationDTO msg = NotificationDTO.builder()
                 .userId(61)
                 .title("基金收益提醒")
                 .message(message.toString())
                 .url("/trade/fund")
                 .type(NotificationConstant.NOTIFICATION_FUND_TIPS)
                 .build();
        this.notificationService.saveNotifyToUser(msg);
    }

    /**
     * websocket监控数据推送
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void notifyFundWebsocketData() {
        List<FundBeanVO> resultList = this.getRedisData();
        if (CollectionUtils.isEmpty(resultList)) {
            return;
        }

        // 推送消息
        NotificationDTO msg = NotificationDTO.builder()
                .title("基金收益提醒")
                .message(JSON.toJSONString(resultList, SerializerFeature.WriteNullStringAsEmpty))
                .url("/dashboard")
                .type(NotificationConstant.NOTIFICATION_FUND_TIPS)
                .build();
        this.notificationService.sendToTopic(msg);
    }


    private List<FundBeanVO> getRedisData(){
        List<FundBeanVO> resultList = Lists.newArrayList();

        List<String> apiParams = this.fundService.getApiParams();
        for (String str : apiParams) {

            String[] strArray = str.contains(",") ? str.split(",") : new String[]{str};
            String redisKey = "FUND_JZ_DATA:" + strArray[0] + ":" + DateFormatUtils.format(new Date(), "yyyy-MM-dd");

            // 获取最新的一条数据
            Set<FundBeanVO> fundBeanVOS = this.redisService.zRevRange(redisKey, FundBeanVO.class, 0, 0, RedisDbConstant.REDIS_DB_DEFAULT);
            if (CollectionUtils.isNotEmpty(fundBeanVOS)) {
                resultList.addAll(fundBeanVOS);
            }
        }
        return resultList;
    }

    /**
     * 刷新基金实时走势
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void refreshFundData() {

        if (!isOpen(LocalDateTime.now())) {
            return;
        }
        List<FundBeanVO> fundBeanVOS = this.fundService.getListData();
        if (CollectionUtils.isEmpty(fundBeanVOS)) {
            return;
        }
        // 持久化到redis
        List<Boolean> saveToRedis = this.saveToRedis(fundBeanVOS);
        // 打印日志
        this.printLog(fundBeanVOS);
    }

    private List<Boolean> saveToRedis(List<FundBeanVO> fundBeanVOS) {

        List<Boolean> result = Lists.newArrayList();
        for (FundBeanVO fund : fundBeanVOS) {

            String redisKey = "FUND_JZ_DATA:" + fund.getFundCode() + ":" + DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            long timestamp = new Date().getTime() / 1000;

            Boolean success = this.redisService.zSetAdd(redisKey, fund, timestamp, RedisDbConstant.REDIS_DB_DEFAULT);
            result.add(success);
        }
        return result;
    }

    private void printLog(List<FundBeanVO> fundBeanVOS) {
        for (FundBeanVO fund : fundBeanVOS) {
            String msg = String.format(
                    "[编码: %s, 基金名称: %s, 估算净值: %s, 估算涨跌: %s, 更新时间: %s, 收益率: %s, 收益: %s, 今日收益: %s] ",
                    fund.getFundCode(),
                    fund.getFundName(),
                    fund.getGsz(),
                    fund.getGszzl() != null ? (fund.getGszzl().startsWith("-") ? fund.getGszzl() : "+" + fund.getGszzl()) + "%" : "-",
                    fund.getGztime() != null ? fund.getGztime() : "-",
                    fund.getIncomePercent() != null ? fund.getIncomePercent() + "%" : "-",
                    fund.getIncome() != null ? fund.getIncome() : "-",
                    fund.getTodayIncome() != null ? fund.getTodayIncome() : "-"
            );
            log.info(msg);
        }
        log.info("=========================================================================================================================================================");
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
            return time.isAfter(LocalTime.of(13, 0)) && time.isBefore(LocalTime.of(18, 30));
        }
        return false;
    }
}
