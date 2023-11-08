package com.coderman.bizedu.sync.config;

import com.coderman.bizedu.sync.listener.ActiveMqListener;
import lombok.Data;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;
import javax.jms.Session;

/**
 * @author zhangyukang
 */
@Configuration
@ConfigurationProperties(prefix = "sync.activemq")
@Data
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
        cachingConnectionFactory.setMaxConnections(3);
        return cachingConnectionFactory;
    }

    /**
     * activeMQ连接工厂
     *
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMqConnectionFactory() {
        return new ActiveMQConnectionFactory(username, password, brokerUrl);
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
        container.setConcurrentConsumers(3);
        container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        container.setSessionTransacted(false);
        container.setPubSubDomain(false);
        return container;
    }
}
