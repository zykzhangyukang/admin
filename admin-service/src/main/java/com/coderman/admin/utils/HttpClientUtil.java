package com.coderman.admin.utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private static final PoolingHttpClientConnectionManager connectionManager;
    private static final RequestConfig requestConfig;
    private static final CloseableHttpClient httpClient;
    private static final int MAX_TIMEOUT = 10000;

    // 初始化连接池和 HttpClient
    static {
        // 连接池配置
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(200); // 最大连接数
        connectionManager.setDefaultMaxPerRoute(50); // 每个路由的最大连接数

        // 请求配置
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(MAX_TIMEOUT) // 连接超时
                .setSocketTimeout(MAX_TIMEOUT)  // 读取超时
                .setConnectionRequestTimeout(MAX_TIMEOUT) // 从连接池获取连接的超时
                .build();

        // 创建 HttpClient，使用连接池
        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        // 在 JVM 关闭时释放连接池资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                connectionManager.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * 发送 GET 请求，支持自定义请求头
     * @param url 请求 URL
     * @param headers 自定义请求头
     * @return 响应结果字符串
     * @throws IOException 发生 IO 异常时抛出
     */
    public static String doGet(String url, Map<String, String> headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);

        // 设置请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
        }
    }

    /**
     * 发送 POST 请求，支持自定义请求头和 JSON 请求体
     * @param url 请求 URL
     * @param json 请求体 JSON 字符串
     * @param headers 自定义请求头
     * @return 响应结果字符串
     * @throws IOException 发生 IO 异常时抛出
     */
    public static String doPost(String url, String json, Map<String, String> headers) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");

        // 设置自定义请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 设置请求体
        if (json != null) {
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(stringEntity);
        }

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
        }
    }

    /**
     * 发送 POST 请求，支持自定义请求头、JSON 请求体和表单参数
     * @param url 请求 URL
     * @param json 请求体 JSON 字符串
     * @param headers 自定义请求头
     * @param params 表单参数
     * @return 响应结果字符串
     * @throws IOException 发生 IO 异常时抛出
     */
    public static String doPost(String url, String json, Map<String, String> headers, Map<String, String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);

        // 设置自定义请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 设置请求体
        if (json != null) {
            httpPost.setHeader("Content-Type", "application/json");
            HttpEntity stringEntity = new StringEntity(json, StandardCharsets.UTF_8);
            httpPost.setEntity(stringEntity);
        } else if (params != null && !params.isEmpty()) {
            // 设置表单参数
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            List<BasicNameValuePair> urlParams = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            HttpEntity formEntity = new UrlEncodedFormEntity(urlParams, StandardCharsets.UTF_8);
            httpPost.setEntity(formEntity);
        }

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity, StandardCharsets.UTF_8) : null;
        }
    }
}
