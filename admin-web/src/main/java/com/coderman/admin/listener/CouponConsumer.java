package com.coderman.admin.listener;

import com.alibaba.fastjson.JSON;
import com.coderman.redis.queue.QueueListener;
import com.coderman.redis.queue.QueueMessage;
import com.coderman.redis.queue.QueueMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ：zhangyukang
 * @date ：2025/04/22 11:27
 */
@Component
@QueueListener(queue = "coupon-queue", maxRetries = 6, retryDelay = 3000, threadCount = 2)
@Slf4j
public class CouponConsumer implements QueueMessageHandler {

    public void handle(QueueMessage message) {

        log.info("处理优惠券消息:{}", JSON.toJSONString(message));
        if (message.getPayload().contains("fail")) {
            throw new RuntimeException("CouponConsumer#模拟失败");
        }
    }
}
