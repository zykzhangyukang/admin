package com.coderman.admin.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.service.common.FundService;
import com.coderman.admin.utils.HttpClientUtil;
import com.coderman.admin.vo.common.FundBeanVO;
import com.coderman.admin.vo.common.FundSettingItemVO;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.redis.service.RedisService;
import com.coderman.service.anntation.LogError;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author ：zhangyukang
 * @date ：2024/10/24 14:38
 */
@Service
@Slf4j
public class FundServiceImpl implements FundService {

    @Resource
    private RedisService redisService;

    @Override
    @LogError(value = "基金列表")
    public List<FundBeanVO> getListData() {

        List<String> codes = this.getApiParams();

        List<String> codeList = new ArrayList<>();
        Map<String, String[]> codeMap = new HashMap<>();

        for (String str : codes) {
            // 兼容原有设置
            String[] strArray = str.contains(",") ? str.split(",") : new String[]{str};
            codeList.add(strArray[0]);
            codeMap.put(strArray[0], strArray);
        }

        List<FundBeanVO> fundBeanVOS = Lists.newArrayList();
        for (String code : codeList) {
            try {
                fundBeanVOS.add(this.fetchListData(code, codeMap));
            } catch (Exception e) {
                log.error("处理基金编码 [{}] 时发生异常: {}", code, e.getMessage(), e);
            }
        }
        return fundBeanVOS;
    }

    @Override
    @LogError(value = "获取历史净值")
    public JSONObject getHistoryData(Integer currentPage, Integer pageSize, String code) throws IOException {

        currentPage = Optional.ofNullable(currentPage).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(20);

        Map<String, String> headers = getHeaderMap();

        String result = HttpClientUtil.doGet("https://api.fund.eastmoney.com/f10/lsjz?callback=jQuery18309019060760859061_1729219122448&fundCode=" + code +
                        "&pageIndex=" + currentPage + "&pageSize=" + pageSize + "&startDate=&endDate=&_=" + System.currentTimeMillis(),
                headers
        );

        Assert.notNull(result , "获取数据错误!");

        int startIndex = result.indexOf('(') + 1;
        int endIndex = result.lastIndexOf(')');

        String jsonResponse = result.substring(startIndex, endIndex);
        JSONObject jsonObject = JSON.parseObject(jsonResponse);
        return jsonObject.getJSONObject("Data");
    }

    private static Map<String, String> getHeaderMap() {
        Map<String,String> headers = Maps.newHashMap();

        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("cache-control", "no-cache");
        headers.put("pragma", "no-cache");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "script");
        headers.put("sec-fetch-mode", "no-cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("referer", "https://fundf10.eastmoney.com/");
        headers.put("referrerPolicy", "strict-origin-when-cross-origin");
        return headers;
    }

    @Override
    public List<JSONObject> getSearchData() throws IOException {
        String result = HttpClientUtil.doGet("http://fund.eastmoney.com/js/fundcode_search.js", Maps.newHashMap());
        Assert.notNull(result, "获取数据错误!");
        return Lists.newArrayList();
    }

    @Override
    @LogError(value = "保存基金设置")
    public ResultVO<Void> saveSetting(List<FundSettingItemVO> settingInfoVO) {
        this.redisService.setString("FUND_SETTING_DATA", JSONObject.toJSONString(settingInfoVO), RedisDbConstant.REDIS_DB_DEFAULT);
        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "获取基金配置")
    public ResultVO<List<FundSettingItemVO>> getSetting() {
        String settingData = this.redisService.getString("FUND_SETTING_DATA", RedisDbConstant.REDIS_DB_DEFAULT);
        List<FundSettingItemVO> settingItemVOs = JSONObject.parseArray(settingData, FundSettingItemVO.class);
        return ResultUtil.getSuccessList(FundSettingItemVO.class, settingItemVOs);
    }

    @Override
    public List<String> getApiParams() {

        ResultVO<List<FundSettingItemVO>> setting = this.getSetting();
        List<String> confList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(setting.getResult())){

            for (FundSettingItemVO settingItemVO : setting.getResult()) {
                StringBuilder str = new StringBuilder(settingItemVO.getFundCode());
                if(Objects.nonNull(settingItemVO.getCostPrise()) && settingItemVO.getCostPrise().compareTo(BigDecimal.ZERO) > 0){
                    str.append(",").append(settingItemVO.getCostPrise());
                }
                if(Objects.nonNull(settingItemVO.getBonds()) && settingItemVO.getBonds().compareTo(BigDecimal.ZERO) > 0){
                    str.append(",").append(settingItemVO.getBonds());
                }
                confList.add(str.toString());
            }
        }
        return confList;
    }

    @Override
    @LogError(value = "导出json配置")
    public void exportSetting(HttpServletResponse response) throws IOException {

        List<FundSettingItemVO> result = Optional.ofNullable( this.getSetting().getResult()).orElse(Lists.newArrayList());
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=config.json");
        response.getWriter().write(JSON.toJSONString(result));
    }

    @Override
    @LogError(value = "导入基金配置")
    public ResultVO<Void> importSetting(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResultUtil.getFail("导入文件不能为空");
        }
        try {
            String jsonContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            List<FundSettingItemVO> settingItemVOS = JSON.parseArray(jsonContent, FundSettingItemVO.class);
            if (CollectionUtils.isEmpty(settingItemVOS)) {
                return ResultUtil.getWarn("配置文件为空");
            }
            // 保存配置
            this.saveSetting(settingItemVOS);
        } catch (Exception e) {
            return ResultUtil.getFail("解析配置文件错误!");
        }

        return ResultUtil.getSuccess();
    }


    @LogError(value = "天天基金信息获取")
    private FundBeanVO fetchListData(String code, Map<String, String[]> codeMap) throws IOException {

        FundBeanVO bean = null;

        String result = HttpClientUtil.doGet("http://fundgz.1234567.com.cn/js/" + code + ".js?rt=" + System.currentTimeMillis(), Maps.newHashMap());
        Assert.notNull(result, "获取数据错误!");

        String json = result.substring(8, result.length() - 2);
        if (!json.isEmpty()) {
            bean = JSON.parseObject(json, FundBeanVO.class);
            FundBeanVO.loadFund(bean, codeMap);

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

            try {

                // 获取近30天的历史净值数据
                String key = "FUND_HISTORY_DATA:" + code + ":" + DateFormatUtils.format(new Date(), "yyyy-MM-dd");
                JSONArray list = this.redisService.getObject(key, JSONArray.class, RedisDbConstant.REDIS_DB_DEFAULT);
                if (list == null || list.isEmpty()) {
                    JSONObject historyData = this.getHistoryData(1, 30, code);
                    list = historyData.getJSONArray("LSJZList");
                    this.redisService.setObject(key, list, 3600, RedisDbConstant.REDIS_DB_DEFAULT);
                }

                // 5日、10日、20日、30日
                BigDecimal average5 = list.stream()
                        .limit(5)
                        .map(o -> ((JSONObject) o).getBigDecimal("DWJZ"))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(new BigDecimal(5), 2, RoundingMode.HALF_DOWN);

                BigDecimal average10 = list.stream()
                        .limit(10)
                        .map(o -> ((JSONObject) o).getBigDecimal("DWJZ"))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(new BigDecimal(10), 2, RoundingMode.HALF_DOWN);

                BigDecimal average20 = list.stream()
                        .limit(20)
                        .map(o -> ((JSONObject) o).getBigDecimal("DWJZ"))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(new BigDecimal(20), 2, RoundingMode.HALF_DOWN);

                BigDecimal average30 = list.stream()
                        .limit(30)
                        .map(o -> ((JSONObject) o).getBigDecimal("DWJZ"))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(new BigDecimal(30), 2, RoundingMode.HALF_DOWN);

                bean.setJz5(average5.toString());
                bean.setJz10(average10.toString());
                bean.setJz20(average20.toString());
                bean.setJz30(average30.toString());

            } catch (Exception e) {
                log.error("计算基金编码 [{}] 的近15天和7天均值时发生异常: {}", code, e.getMessage(), e);
            }

        } else {
            log.error("Fund编码:[" + code + "]无法获取数据");
        }

        return bean;
    }
}
