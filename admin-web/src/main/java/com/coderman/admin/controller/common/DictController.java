package com.coderman.admin.controller.common;

import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.constant.ResultConstant;
import com.coderman.api.vo.ResultVO;
import com.coderman.limiter.annotation.RateLimit;
import com.coderman.limiter.resolver.IpKeyResolver;
import com.coderman.redis.service.RedisService;
import com.coderman.service.dict.ConstItems;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author zhangyukang
 */
@Api(value = "字典接口", tags = "字典接口")
@RestController
@Slf4j
public class DictController {

    @Resource
    private RedisService redisService;

    /**
     * 系统常量获取
     *
     * @return
     */
    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "常量列表")
    @GetMapping(value = "/auth/const/all")
    @SuppressWarnings("all")
    @RateLimit(keyResolver = IpKeyResolver.class)
    public ResultVO<Map<String, List<ConstItems>>> constAll() {

        Map<String, List<ConstItems>> resultMap = new HashMap<String, List<ConstItems>>();

        this.redisService.getRedisTemplate().execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                StringRedisSerializer s1 = new StringRedisSerializer();
                GenericJackson2JsonRedisSerializer s2 = new GenericJackson2JsonRedisSerializer();

                connection.select(RedisDbConstant.REDIS_DB_DEFAULT);

                byte[] hashKey = s1.serialize("auth.const.all");

                Set<byte[]> fields = connection.hKeys(hashKey);

                for (byte[] field : fields) {

                    String project = new String(field);
                    List<ConstItems> constItems = (List<ConstItems>) s2.deserialize(connection.hGet(hashKey, field));

                    resultMap.put(project, constItems);
                }

                return null;
            }
        });

        ResultVO<Map<String, List<ConstItems>>> resultVO = new ResultVO<>();
        resultVO.setCode(ResultConstant.RESULT_CODE_200);
        resultVO.setResult(resultMap);
        return resultVO;
    }
}
