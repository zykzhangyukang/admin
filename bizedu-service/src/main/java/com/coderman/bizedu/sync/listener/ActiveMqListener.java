package com.coderman.bizedu.sync.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class ActiveMqListener implements SessionAwareMessageListener<TextMessage> {

    @Override
    public void onMessage(@NonNull TextMessage textMessage,@NonNull Session session) throws JMSException {


        log.info("接受到消息------------------："+ textMessage.getText());

        textMessage.acknowledge();
    }
}
