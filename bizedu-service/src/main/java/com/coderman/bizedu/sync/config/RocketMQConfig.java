package com.coderman.bizedu.sync.config;

import com.coderman.bizedu.sync.listener.RocketMqListener;
import com.coderman.bizedu.sync.listener.RocketMqOrderListener;
import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zhangyukang
 */
@Configuration
@ConfigurationProperties(prefix = "sync.rocketmq")
@ConditionalOnProperty(name = "sync.mq.type", havingValue = "rocketmq")
@Data
public class RocketMQConfig {

    @Resource
    private RocketMqListener rocketMqListener;

    @Resource
    private RocketMqOrderListener rocketMqOrderListener;

    private String namesrvAddr;

    private String instanceName;

    private String consumerGroup;

    private String consumerOrderGroup;

    private String topic;

    private String orderTopic;

    private Integer consumeThreadMin;

    private Integer consumeThreadMax;


    @Bean(value = "defaultMQPushConsumer", initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {

        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
        mqPushConsumer.setNamesrvAddr(namesrvAddr);
        mqPushConsumer.setInstanceName(instanceName);
        mqPushConsumer.setConsumerGroup(consumerGroup);
        mqPushConsumer.subscribe(topic, "*");
        mqPushConsumer.setMessageListener(rocketMqListener);
        mqPushConsumer.setConsumeThreadMin(consumeThreadMin);
        mqPushConsumer.setConsumeThreadMax(consumeThreadMax);
        return mqPushConsumer;
    }

    @Bean(value = "orderlyMQPushConsumer", initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer orderlyMQPushConsumer() throws MQClientException {

        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
        mqPushConsumer.setInstanceName(instanceName);
        mqPushConsumer.setNamesrvAddr(namesrvAddr);
        mqPushConsumer.setConsumerGroup(consumerOrderGroup);
        mqPushConsumer.subscribe(orderTopic, "*");
        mqPushConsumer.setMessageListener(rocketMqOrderListener);
        mqPushConsumer.setConsumeThreadMin(consumeThreadMin);
        mqPushConsumer.setConsumeThreadMax(consumeThreadMax);
        mqPushConsumer.setSuspendCurrentQueueTimeMillis(2000);
        return mqPushConsumer;
    }

}
