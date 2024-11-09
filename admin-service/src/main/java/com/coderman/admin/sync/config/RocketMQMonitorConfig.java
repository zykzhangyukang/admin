package com.coderman.admin.sync.config;

import com.coderman.sync.properties.SyncProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@EnableConfigurationProperties({SyncProperties.class})
@ConditionalOnProperty(prefix = "sync", name = "mq", havingValue = "rocketmq")
public class RocketMQMonitorConfig {


    @Bean(value = "defaultMQAdminExt",initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQAdminExt defaultMQAdminExt(SyncProperties syncProperties) {
        DefaultMQAdminExt mqAdminExt = new DefaultMQAdminExt();
        mqAdminExt.setNamesrvAddr(syncProperties.getRocketmq().getNamesrvAddr());
        return mqAdminExt;
    }


}


