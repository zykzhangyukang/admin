package com.coderman.admin.listener;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.redis.queue.SimpleQueueManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：zhangyukang
 * @date ：2025/04/22 12:00
 */
@RestController
public class TestController {

    @Resource
    private SimpleQueueManager simpleQueueManager;

    @GetMapping(value = "/test/push")
    public ResultVO<Void> push(String payload){
        simpleQueueManager.enqueue("email-queue", payload);
        simpleQueueManager.enqueue("coupon-queue", payload);
        return ResultUtil.getSuccess();
    }
}
