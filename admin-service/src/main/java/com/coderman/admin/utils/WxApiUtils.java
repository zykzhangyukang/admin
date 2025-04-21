package com.coderman.admin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.redis.service.RedisService;
import com.coderman.service.util.DesUtil;
import com.coderman.service.util.SpringContextUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 微信 API 工具类
 * 提供获取企业微信 Access Token 和发送消息等功能
 *
 * @author ：zhangyukang
 * @date ：2024/10/31 16:01
 */
@Slf4j
public class WxApiUtils {

    private static final String TOKEN_CACHE_KEY = "qw_token_cache_key";
    private static final String CORP_ID = "573304AB8CE9C6CA54B826CB0568C6816265408F43056521";
    private static final String CORP_SECRET = "5D1C42C9DC2AD98E0B3B20ABB3DB79B83409BF99FF38FA12C475D6E17220F797AB19AC605AD6BBDDB705E95F368D1842";
    private static final Integer AGENT_ID = 1000021;

    private final RedisService redisService;

    private static class Holder {
        private static final WxApiUtils INSTANCE = new WxApiUtils();
    }

    public static WxApiUtils getInstance() {
        return Holder.INSTANCE;
    }

    private WxApiUtils() {
        redisService = SpringContextUtil.getBean(RedisService.class);
    }

    /**
     * 获取企业微信 Access Token
     *
     * @return Access Token
     */
    public String getAccessToken() {

        String token = redisService.getString(TOKEN_CACHE_KEY, RedisDbConstant.REDIS_DB_DEFAULT);
        if (StringUtils.isNotBlank(token)) {
            return token;
        }

        try {
            // 如果缓存没有，就请求微信接口
            String result = HttpClientUtil.doGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + CORP_ID + "&corpsecret=" + DesUtil.decrypt(CORP_SECRET), Maps.newHashMap());
            JSONObject jsonObject = JSON.parseObject(result);
            if (!StringUtils.equals(jsonObject.getString("errmsg"), "ok")) {
                throw new BusinessException("获取token失败，错误信息：" + jsonObject.toJSONString());
            }

            // 获取 Access Token 并缓存
            String accessToken = jsonObject.getString("access_token");
            Integer expiresIn = jsonObject.getInteger("expires_in");
            redisService.setString(TOKEN_CACHE_KEY, accessToken, expiresIn - 60, RedisDbConstant.REDIS_DB_DEFAULT);

            return accessToken;
        } catch (IOException e) {
            log.error("获取企业微信 Access Token 失败: {}", e.getMessage(), e);
            throw new BusinessException("获取企业微信 Token 失败");
        }
    }

    /**
     * 发送消息到企业微信
     *
     * @param wxUserIds 微信用户 ID 列表
     * @param content 消息内容
     * @return 微信 API 返回的响应
     */
    public JSONObject sendMessage(List<String> wxUserIds, String content) {
        Assert.isTrue(StringUtils.isNotBlank(content), "消息内容不能为空");
        Assert.notEmpty(wxUserIds, "微信接受人不能为空");

        try {
            String accessToken = getAccessToken();

            // 构建消息体
            Message message = new Message();
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("content", content);
            String toUser = wxUserIds.stream().distinct().collect(Collectors.joining("|"));
            message.setTouser(toUser)
                    .setAgentid(AGENT_ID)
                    .setMsgtype("text")
                    .setSafe(0)
                    .setText(contentMap);

            // 发送消息
            String result = HttpClientUtil.doPost("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="
                    + accessToken, JSON.toJSONString(message), Maps.newHashMap(), Maps.newHashMap());
            JSONObject jsonObject = JSON.parseObject(result);
            if (!StringUtils.equals(jsonObject.getString("errmsg"), "ok")) {
                throw new BusinessException("发送消息失败，错误信息：" + jsonObject.toJSONString());
            }

            return jsonObject;
        } catch (IOException e) {
            log.error("发送消息失败: {}", e.getMessage(), e);
            throw new BusinessException("发送消息失败");
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Message {
        private String touser;
        private String toparty;
        private String totag;
        private String msgtype;
        private Integer agentid;
        private Map<String, String> text;
        private Map<String, String> textcard;
        private Map<String, String> file;
        private String enable_id_trans;
        private Integer safe;
        private Integer enable_duplicate_check;
        private Integer deplicate_check_interval;
    }
}
