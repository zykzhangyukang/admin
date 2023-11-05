package com.coderman.bizedu.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Slf4j
public class ActiveMQServer {


    @Bean
    public BrokerService brokerService() throws Exception {
        BrokerService broker = new BrokerService();

        // 这里要使用服务器端内网ip
        broker.addConnector("tcp://10.41.188.160:61616");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ActiveMQServer启动>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return broker;
    }

}
