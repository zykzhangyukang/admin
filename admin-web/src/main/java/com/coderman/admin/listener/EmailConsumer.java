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
@QueueListener(queue = "email-queue", maxRetries = 4, retryDelay = 3000, threadCount = 2)
@Slf4j
public class EmailConsumer implements QueueMessageHandler {

    public void handle(QueueMessage message) {

        log.info("处理邮件消息:{}", JSON.toJSONString(message));
        if (message.getPayload().contains("fail")) {
            throw new RuntimeException("EmailConsumer#模拟失败");
        }
    }
}
