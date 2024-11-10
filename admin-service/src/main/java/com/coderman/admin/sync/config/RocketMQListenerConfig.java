package com.coderman.admin.sync.config;

import com.coderman.admin.sync.listener.RocketMqListener;
import com.coderman.admin.sync.listener.RocketMqOrderListener;
import com.coderman.admin.sync.service.ResultService;
import com.coderman.sync.properties.SyncProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@EnableConfigurationProperties({SyncProperties.class})
@ConditionalOnProperty(prefix = "sync", name = "mq", havingValue = "rocketmq")
public class RocketMQListenerConfig {


    @Bean(value = "defaultMQPushConsumer", initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer defaultMQPushConsumer(SyncProperties syncProperties, ResultService resultService) throws MQClientException {


        SyncProperties.RocketMQ properties = syncProperties.getRocketmq();

        RPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(properties.getUsername(), properties.getPassword()));

        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer(rpcHook);
        mqPushConsumer.setNamesrvAddr(properties.getNamesrvAddr());
        mqPushConsumer.setInstanceName(properties.getInstantName());
        mqPushConsumer.setConsumerGroup(properties.getConsumerGroup());
        mqPushConsumer.subscribe(properties.getSyncTopic(), "*");
        mqPushConsumer.setMessageListener(new RocketMqListener(resultService));
        mqPushConsumer.setConsumeThreadMin(properties.getConsumeThreadMin());
        mqPushConsumer.setConsumeThreadMax(properties.getConsumeThreadMax());
        return mqPushConsumer;
    }

    @Bean(value = "orderlyMQPushConsumer", initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer orderlyMQPushConsumer(SyncProperties syncProperties, ResultService resultService) throws MQClientException {

        SyncProperties.RocketMQ properties = syncProperties.getRocketmq();

        RPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(properties.getUsername(), properties.getPassword()));

        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer(rpcHook);
        mqPushConsumer.setNamesrvAddr(properties.getNamesrvAddr());
        mqPushConsumer.setInstanceName(properties.getInstantName());
        mqPushConsumer.setConsumerGroup(properties.getConsumerOrderGroup());
        mqPushConsumer.subscribe(properties.getSyncOrderTopic(), "*");
        mqPushConsumer.setMessageListener(new RocketMqOrderListener(resultService));
        mqPushConsumer.setConsumeThreadMin(properties.getConsumeThreadMin());
        mqPushConsumer.setConsumeThreadMax(properties.getConsumeThreadMax());
        mqPushConsumer.setSuspendCurrentQueueTimeMillis(2000);
        return mqPushConsumer;
    }

}
