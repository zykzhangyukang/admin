package com.coderman.admin.sync.config;

import com.coderman.sync.properties.SyncProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author 嵌入式activeMQ配置
 */
@ImportResource("classpath:embedded-activemq.xml")
@EnableConfigurationProperties({SyncProperties.class})
@ConditionalOnProperty(prefix = "sync", name = "mq", havingValue = "activemq")
@Configuration
public class EmbeddedActiveMQConfig {
}