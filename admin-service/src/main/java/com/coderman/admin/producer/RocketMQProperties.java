package com.coderman.admin.producer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sync.rocketmq")
@Data
@Slf4j
public class RocketMQProperties {

    @ApiModelProperty(value = "生产者组")
    private String producerGroup;

    @ApiModelProperty(value = "生产者组(顺序)")
    private String producerOrderGroup;

    @ApiModelProperty(value = "nameServer地址")
    private String namesrvAddr;

    @ApiModelProperty(value = "实例名称")
    private String instantName;

    @ApiModelProperty(value = "同步消息主题")
    private String syncTopic;

    @ApiModelProperty(value = "同步消息主题(顺序)")
    private String syncOrderTopic;

    @ApiModelProperty(value = "发送超时时间")
    private int sendMsgTimeoutMillis;

    @ApiModelProperty(value = "重试次数")
    private int retryTimes;

}
