package com.coderman.admin.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderman.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ：zhangyukang
 * @date ：2024/10/12 14:27
 */
@Component
@Slf4j
public class BaiduHotSpider {

    @Resource
    private RedisService redisService;


    @Scheduled(cron = "0 * * * * *")
    //@Scheduled(cron = "*/5 * * * * *")
    public void fetchData() throws IOException {
        Document doc = Jsoup.connect("http://www.baidu.com").timeout(5000).get();

        Element hotsearchData = doc.getElementById("hotsearch_data");
        JSONObject jsonObject = JSON.parseObject(hotsearchData.text());
        JSONArray list = jsonObject.getJSONArray("hotsearch");

        Set<RedisZSetCommands.Tuple> tupleSet = new HashSet<>();
        for (Object o : list) {
            JSONObject item = (JSONObject) o;
            String title = item.getString("card_title");
            Long score = item.getLong("heat_score");

            // 将 title 和 score 作为 Tuple 添加到 set 中
            RedisZSetCommands.Tuple tuple = new DefaultTuple(title.getBytes(), score.doubleValue());
            tupleSet.add(tuple);
        }

        this.redisService.getRedisTemplate().execute((RedisCallback<Object>) redisConnection -> {
            String redisKey = "hotsearch_data:" + DateFormatUtils.format(new Date(), "yyyyMMdd");
            redisConnection.zAdd(redisKey.getBytes(), tupleSet);
            return null;
        });

        log.info("fetchData url [http://www.baidu.com], result:{}", tupleSet.size());
    }


    /**
     * 获取 zset 中前 10 条记录，支持排序方式（升序/降序）。
     *
     * @param order 排序方式，"asc" 表示升序，"desc" 表示降序
     * @return 前 10 条记录的集合
     */
    @SuppressWarnings("unchecked")
    public JSONArray getTopRecords(String order, Integer topN) {

        String redisKey = "hotsearch_data:" + DateFormatUtils.format(new Date(), "yyyyMMdd");

        // 执行 Redis 操作
        Set<RedisZSetCommands.Tuple> tupleSet = (Set<RedisZSetCommands.Tuple>) this.redisService.getRedisTemplate().execute((RedisCallback<Set<RedisZSetCommands.Tuple>>) redisConnection -> {
            if ("desc".equalsIgnoreCase(order)) {
                return redisConnection.zRevRangeWithScores(redisKey.getBytes(), 0, topN - 1);
            } else {
                return redisConnection.zRangeWithScores(redisKey.getBytes(), 0, topN - 1);
            }
        });

        JSONArray jsonArray = new JSONArray();
        if (CollectionUtils.isEmpty(tupleSet)) {
            return jsonArray;
        }

        for (RedisZSetCommands.Tuple tuple : tupleSet) {
            String title = new String(tuple.getValue(), StandardCharsets.UTF_8);
            JSONObject item = new JSONObject();
            item.put("title", title);
            item.put("score", tuple.getScore());
            jsonArray.add(item);
        }

        return jsonArray;
    }

}
