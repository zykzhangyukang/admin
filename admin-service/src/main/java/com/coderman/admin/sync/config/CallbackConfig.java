package com.coderman.admin.sync.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangyukang
 */
@Configuration
@ConfigurationProperties(prefix = "sync.callback")
@Data
public class CallbackConfig {

    @ApiModelProperty(value = "回调服务列表")
    private List<Callback> destList;

    @Data
    public static class Callback implements Serializable {

        @ApiModelProperty(value = "回调的消息")
        private String project;

        @ApiModelProperty(value = "回调url")
        private String url;
    }

}