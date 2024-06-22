package com.coderman.admin.sync.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyukang
 */
@Configuration
@ConfigurationProperties(prefix = "sync.elasticsearch")
@Data
public class ElasticsearchConfig {

    private String clusterName;
    private String hosts;
    private String scheme;
    private Integer connectTimeOut;
    private Integer socketTimeOut;
    private Integer connectionRequestTimeOut;
    private Integer maxConnectNum;
    private Integer maxConnectNumPerRoute;
    private String username;
    private String password;

    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {

        List<HttpHost> hostLists = new ArrayList<>();
        String[] hostList = hosts.split(",");
        for (String addr : hostList) {
            String host = addr.split(":")[0];

            String port;
            if ("https".equals(scheme) && !StringUtils.contains(addr, ":")) {
                port = "443";
            } else if ("http".equals(scheme) && !StringUtils.contains(addr, ":")) {
                port = "80";
            } else {
                port = addr.split(":")[1];
            }

            hostLists.add(new HttpHost(host, Integer.parseInt(port), scheme));
        }

        // 转换成 HttpHost 数组
        HttpHost[] httpHost = hostLists.toArray(new HttpHost[]{});

        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);

        // 添加身份验证
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        // 连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeOut);
            requestConfigBuilder.setSocketTimeout(socketTimeOut);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
            return requestConfigBuilder;
        });

        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            // 连接数配置
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectNumPerRoute);
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

            //显式设置keepAliveStrategy (TCP探活)
            httpClientBuilder.setKeepAliveStrategy((httpResponse,httpContext) -> TimeUnit.MINUTES.toMillis(3));
            //显式开启tcp keepalive
            httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setSoKeepAlive(true).build());
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }

}
