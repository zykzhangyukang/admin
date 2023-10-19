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
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangyukang
 * @date ：2023/10/19 14:14
 */
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisService redisService;
    @Resource
    private UserService userApi;


    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse serverHttpResponse, @NotNull WebSocketHandler webSocketHandler, @NotNull Map<String, Object> map) throws Exception {

        List<String> tokens = request.getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (CollectionUtils.isEmpty(tokens)) {
            logger.error("未登录系统，禁止连接WebSocket");
            return false;
        }

        String token = tokens.get(0);

        ResultVO<AuthUserVO> resultVO = this.userApi.getUserByToken(token);
        if (!ResultConstant.RESULT_CODE_200.equals(resultVO.getCode())) {
            logger.error("未登录系统，禁止连接WebSocket , resultVO:{}", JSON.toJSON(resultVO));
            return false;
        }

        AuthUserVO authUserVO = resultVO.getResult();
        if (this.redisService.isSetMember(RedisConstant.WEBSOCKET_USER_SET, authUserVO.getUsername(), RedisDbConstant.REDIS_DB_DEFAULT)) {

            logger.error("同一个用户不准建立多个连接WebSocket");
            return false;
        }

        logger.debug(MessageFormat.format("用户{0}请求建立WebSocket连接", authUserVO.getUsername()));
        return true;
    }

    @Override
    public void afterHandshake(@NotNull ServerHttpRequest serverHttpRequest,@NotNull  ServerHttpResponse serverHttpResponse, @NotNull WebSocketHandler webSocketHandler, Exception e) {

    }

}
