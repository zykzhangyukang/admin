package com.coderman.admin.producer;

import com.coderman.service.service.BaseService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

/**
 * rocketMQ生产者 - 顺序消息
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class RocketMQProducer extends BaseService {

    private DefaultMQProducer defaultMQProducer;

    @Resource
    private RocketMQProperties rocketMQProperties;


    @PostConstruct
    public void init() throws MQClientException {
        this.defaultMQProducer = new DefaultMQProducer(rocketMQProperties.getProducerGroup());
        defaultMQProducer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        defaultMQProducer.setInstanceName(rocketMQProperties.getInstantName());
        defaultMQProducer.setCreateTopicKey(rocketMQProperties.getSyncTopic());
        defaultMQProducer.setSendMsgTimeout(rocketMQProperties.getSendMsgTimeoutMillis());
        defaultMQProducer.setRetryTimesWhenSendFailed(rocketMQProperties.getRetryTimes());
        defaultMQProducer.start();

        logger.info("rocketMQ初始化生产者完成[productGroup:{},instantName:{}]", rocketMQProperties.getProducerGroup(), rocketMQProperties.getInstantName());
    }

    @PreDestroy
    public void destroy() {
        this.defaultMQProducer.shutdown();
        logger.info("rocketMQ生产者[productGroup:{},instantName:{}]销毁", rocketMQProperties.getProducerGroup(), rocketMQProperties.getInstantName());
    }

    public SendResult send(Message message) throws Exception {
        return this.defaultMQProducer.send(message);
    }

    public SendResult send(List<Message> messageList) throws Exception {
        return this.defaultMQProducer.send(messageList);
    }
}
