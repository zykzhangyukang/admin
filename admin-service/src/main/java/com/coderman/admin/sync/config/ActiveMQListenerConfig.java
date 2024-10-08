package com.coderman.admin.sync.config;

import com.coderman.admin.sync.listener.ActiveMqListener;
import com.coderman.admin.sync.service.ResultService;
import com.coderman.sync.properties.SyncProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.lang.NonNull;

/**
 * @author zhangyukang
 */
@Data
@Slf4j
@Configuration
@EnableConfigurationProperties({SyncProperties.class})
@ConditionalOnProperty(prefix = "sync", name = "mq", havingValue = "activemq")
public class ActiveMQListenerConfig {


    /**
     * 连接池的连接工厂，优化Mq的性能
     *
     * @param activeMqConnectionFactory
     * @return
     */
    @Bean
    public PooledConnectionFactory pooledConnectionFactory(@NonNull ActiveMQConnectionFactory activeMqConnectionFactory) {
        PooledConnectionFactory cachingConnectionFactory = new PooledConnectionFactory(activeMqConnectionFactory);
        cachingConnectionFactory.setMaxConnections(4);
        return cachingConnectionFactory;
    }

    /**
     * activeMQ连接工厂
     *
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMqConnectionFactory(SyncProperties syncProperties) {

        SyncProperties.ActiveMQ properties = syncProperties.getActivemq();
        ActiveMQConnectionFactory connectionFactory  = new ActiveMQConnectionFactory(properties.getUsername(), properties.getPassword(), properties.getBrokerUrl());

        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(true);
        // 重试次数设置为5次
        redeliveryPolicy.setMaximumRedeliveries(16);
         // 重试间隔
        redeliveryPolicy.setRedeliveryDelay(1000);
         // 第一次重试之前的等待时间
        redeliveryPolicy.setInitialRedeliveryDelay(1000);
         // 指数递增系数
        redeliveryPolicy.setBackOffMultiplier(4);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(10000);

        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return connectionFactory;
    }

    /**
     * 消息监听容器
     *
     * @param pooledConnectionFactory
     * @return
     */
    @Bean
    public DefaultMessageListenerContainer jmsListenerContainerFactory(PooledConnectionFactory pooledConnectionFactory, SyncProperties syncProperties, ResultService resultService) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(pooledConnectionFactory);
        container.setDestinationName(syncProperties.getActivemq().getQueueName());
        container.setMessageListener(new ActiveMqListener(resultService));
        container.setConcurrentConsumers(4);
        container.setMaxConcurrentConsumers(4);
        // 这里要注意activemq和springboot整合的时候，手动提交为4才生效，和原生的不一样
        container.setSessionAcknowledgeMode(4);
        container.setSessionTransacted(false);
        container.setPubSubDomain(false);
        // 断线重连间隔
        container.setRecoveryInterval(5000);
        return container;
    }
}
