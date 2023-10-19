package com.coderman.bizedu.websocket;

import com.alibaba.fastjson.JSON;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.constant.ResultConstant;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.constant.RedisConstant;
import com.coderman.bizedu.service.user.UserService;
import com.coderman.bizedu.vo.user.AuthUserVO;
import com.coderman.redis.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangyukang
 * @date ：2023/10/19 12:02
 */
@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userApi;

    @Resource
    private RedisService redisService;

    @Override
    protected Principal determineUser(@NotNull ServerHttpRequest request, @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) {

        List<String> tokens = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(tokens)) {
            logger.error("未登录系统，禁止连接WebSocket");
            return null;
        }

        String token = tokens.get(0);

        ResultVO<AuthUserVO> resultVO = this.userApi.getUserByToken(token);
        if (!ResultConstant.RESULT_CODE_200.equals(resultVO.getCode())) {
            logger.error("未登录系统，禁止连接WebSocket , resultVO:{}", JSON.toJSON(resultVO));
            return null;
        }

        AuthUserVO authUserVO = resultVO.getResult();
        // 将用户名存到Redis中
        this.redisService.addToSet(RedisConstant.WEBSOCKET_USER_SET, authUserVO.getUsername(), RedisDbConstant.REDIS_DB_DEFAULT);
        return  new MyPrincipal(authUserVO.getUsername());
    }
}