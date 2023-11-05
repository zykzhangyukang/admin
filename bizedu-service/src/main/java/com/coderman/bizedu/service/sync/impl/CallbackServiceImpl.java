package com.coderman.bizedu.service.sync.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.service.sync.CallbackService;
import com.coderman.callback.SyncCallback;
import com.coderman.callback.SyncMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CallbackServiceImpl implements CallbackService {


    @Override
    @SyncCallback(value = "update_auth_bizedu_user")
    public ResultVO<Void> updateUserCallback(SyncMsg syncMsg) {



        log.info("updateUserCallback回调，syncMsg:{}", JSON.toJSONString(syncMsg));

        return ResultUtil.getFail("业务失败了呀。。。。。。");
    }
}
