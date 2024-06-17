package com.coderman.admin.producer;

import com.coderman.service.util.SpringContextUtil;
import com.coderman.service.util.UUIDUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.lang.NonNull;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author zhangyukang
 */
@Configuration
@ConfigurationProperties(prefix = "sync.activemq.producer")
@Data
@Slf4j
public class ActiveMQProducer {

    private String queueName;

    private String brokerUrl;

    private String username;

    private String password;



    public Message sendMessage(String messageContent) throws JMSException {
        JmsTemplate jmsTemplate = SpringContextUtil.getBean(JmsTemplate.class);
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText(messageContent);
        message.setJMSMessageID("MQ"+UUIDUtils.getPrimaryValue());
        jmsTemplate.convertAndSend(message);
        return message;
    }

    /**
     * 连接池的连接工厂，优化Mq的性能
     *
     * @param activeMqConnectionFactory
     * @return
     */
    @Bean
    public PooledConnectionFactory pooledSyncConnectionFactory(@NonNull ActiveMQConnectionFactory activeMqConnectionFactory) {
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
    public ActiveMQConnectionFactory activeMqSyncConnectionFactory() {
        return new ActiveMQConnectionFactory(username, password, brokerUrl);
    }


    @Bean
    public JmsTemplate jmsSyncTemplate(@Qualifier(value = "pooledSyncConnectionFactory") PooledConnectionFactory pooledConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(pooledConnectionFactory);
        jmsTemplate.setDefaultDestinationName(queueName);
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        jmsTemplate.setSessionTransacted(false);
        return jmsTemplate;
    }
}
