package com.coderman.bizedu.controller.sync;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.sync.producer.ActiveMQProducer;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：zhangyukang
 * @date ：2023/11/06 12:29
 */
@Api(value = "同步系统测试", tags = "同步系统测试")
@RestController
@RequestMapping(value = "/sync")
public class SyncController {

    @Resource
    private ActiveMQProducer activeMQProducer;

    @GetMapping(value = "/test")
    public ResultVO<Void> send(String msg) throws Exception {

        this.activeMQProducer.send(msg);

        return ResultUtil.getSuccess();
    }
}
