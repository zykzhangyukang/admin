package com.coderman.admin.sync.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zhangyukang
 * @date ：2024/08/02 11:29
 */
@Configuration
@ConfigurationProperties(prefix = "sync.db")
@Data
public class SyncDbConfig {

    @ApiModelProperty(value = "消息发送库")
    private String pubMqMessage;

    @ApiModelProperty(value = "回调库")
    private String pubCallback;
}
