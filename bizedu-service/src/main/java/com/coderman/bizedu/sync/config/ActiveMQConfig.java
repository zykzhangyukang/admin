package com.coderman.bizedu.sync.config;

import com.coderman.bizedu.sync.listener.ActiveMqListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;
import javax.jms.Session;

/**
 * @author zhangyukang
 */
@Configuration
@ConfigurationProperties(prefix = "sync.activemq")
@Data
@Slf4j
public class ActiveMQConfig {

    private String queueName;

    private String brokerUrl;

    private String username;

    private String password;

    @Resource
    private ActiveMqListener activeMqListener;

    /**
     * 连接池的连接工厂，优化Mq的性能
     *
     * @param activeMqConnectionFactory
     * @return
     */
    @Bean
    public PooledConnectionFactory pooledConnectionFactory(@NonNull ActiveMQConnectionFactory activeMqConnectionFactory) {
        PooledConnectionFactory cachingConnectionFactory = new PooledConnectionFactory(activeMqConnectionFactory);
        cachingConnectionFactory.setMaxConnections(6);
        return cachingConnectionFactory;
    }

    /**
     * activeMQ连接工厂
     *
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMqConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory  = new ActiveMQConnectionFactory(username, password, brokerUrl);
        // 重试次数设置为16次
        connectionFactory.getRedeliveryPolicy().setMaximumRedeliveries(16);
        return connectionFactory;
    }

    /**
     * 消息监听容器
     *
     * @param pooledConnectionFactory
     * @return
     */
    @Bean
    public DefaultMessageListenerContainer jmsListenerContainerFactory(PooledConnectionFactory pooledConnectionFactory) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(pooledConnectionFactory);
        container.setDestinationName(queueName);
        container.setMessageListener(activeMqListener);
        container.setConcurrentConsumers(6);
        container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        container.setSessionTransacted(false);
        container.setPubSubDomain(false);
        container.setErrorHandler(new ActiveMqListener.LogErrorHandle());
        return container;
    }

    @Bean
    public JmsTemplate jmsTemplate(PooledConnectionFactory pooledConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(pooledConnectionFactory);
        jmsTemplate.setDefaultDestinationName(queueName);
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        jmsTemplate.setSessionTransacted(false);
        return jmsTemplate;
    }
}
