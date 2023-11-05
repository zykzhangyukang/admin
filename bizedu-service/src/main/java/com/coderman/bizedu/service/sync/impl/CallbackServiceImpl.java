package com.coderman.bizedu.service.sync.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.api.constant.RedisDbConstant;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.service.sync.CallbackService;
import com.coderman.callback.SyncCallback;
import com.coderman.callback.SyncMsg;
import com.coderman.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
@Slf4j
public class CallbackServiceImpl implements CallbackService {

    @Resource
    private RedisService redisService;

    @Override
    @SyncCallback(value = "update_auth_bizedu_user")
    public ResultVO<Void> updateUserCallback(SyncMsg syncMsg) {

        int nextInt = new Random().nextInt(5);

        long count = this.redisService.incr(syncMsg.getMsgId(), RedisDbConstant.REDIS_DB_DEFAULT);
        if (count >= nextInt) {

            this.redisService.del(syncMsg.getMsgId(), RedisDbConstant.REDIS_DB_DEFAULT);
            log.error("回调成功，msgId:{}", syncMsg.getMsgId());

            return ResultUtil.getSuccess("回调成功。。。。。。");
        }

        log.info("updateUserCallback回调，syncMsg:{}", JSON.toJSONString(syncMsg));

        return ResultUtil.getFail("业务失败了呀。。。。。。");
    }
}
