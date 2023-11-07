package com.coderman.bizedu.controller.sync;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.sync.producer.ActiveMQProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/sync")
public class TestController {

    @Resource
    private ActiveMQProducer activeMQProducer;

    @GetMapping(value = "/test")
    public ResultVO<String> send(String msg) throws Exception {

        String send = activeMQProducer.send(msg);

        return ResultUtil.getSuccess(String.class, send);
    }
}
