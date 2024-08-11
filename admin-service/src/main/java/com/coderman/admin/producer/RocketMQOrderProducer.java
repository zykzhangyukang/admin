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

/**
 * rocketMQ生产者
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class RocketMQOrderProducer extends BaseService {

    private DefaultMQProducer defaultMQProducer;

    @Resource
    private RocketMQProperties rocketMQProperties;

    @PostConstruct
    public void init() throws MQClientException {
        this.defaultMQProducer = new DefaultMQProducer(rocketMQProperties.getProducerOrderGroup());
        defaultMQProducer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        defaultMQProducer.setCreateTopicKey(rocketMQProperties.getSyncOrderTopic());
        defaultMQProducer.setInstanceName(rocketMQProperties.getInstantName());
        defaultMQProducer.setSendMsgTimeout(rocketMQProperties.getSendMsgTimeoutMillis());
        defaultMQProducer.setRetryTimesWhenSendFailed(rocketMQProperties.getRetryTimes());
        defaultMQProducer.start();

        logger.info("rocketMQ初始化有序生产者完成[productOrderGroup:{},instantName:{}]", rocketMQProperties.getProducerOrderGroup(), rocketMQProperties.getInstantName());
    }

    @PreDestroy
    public void destroy() {
        this.defaultMQProducer.shutdown();
        logger.info("rocketMQ有序生产者[productOrderGroup:{},instantName:{}]销毁",  rocketMQProperties.getProducerOrderGroup(), rocketMQProperties.getInstantName());
    }

    public SendResult send(Message message, String key) throws Exception {

        return this.defaultMQProducer.send(message, (mqs, msg, arg) -> {
            int select = Math.abs(arg.hashCode());
            if(select <0){
                select = 0;
            }
            return mqs.get(select % mqs.size());
        }, key);

    }
}
