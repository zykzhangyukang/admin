package com.coderman.admin.sync.callback;

import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.sync.callback.meta.CallBackNode;
import com.coderman.admin.sync.callback.meta.CallbackTask;
import com.coderman.admin.sync.config.CallbackConfig;
import com.coderman.admin.sync.constant.PlanConstant;
import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.executor.AbstractExecutor;
import com.coderman.admin.sync.sql.SelectBuilder;
import com.coderman.admin.sync.sql.UpdateBuilder;
import com.coderman.admin.sync.sql.meta.SqlMeta;
import com.coderman.admin.sync.task.SyncConvert;
import com.coderman.admin.sync.util.SqlUtil;
import com.coderman.api.constant.ResultConstant;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.service.util.DesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Lazy(false)
@Slf4j
public class CallBackExecutor {

    @Resource
    private CallbackConfig callbackConfig;

    private final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    private HttpClientBuilder httpClientBuilder = null;
    private RequestConfig requestConfig = null;
    private final Map<String, CloseableHttpClient> httpClientMap = new ConcurrentHashMap<>();

    private final Map<String, CallBackNode> callBackNodeMap = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> unFailMap = new ConcurrentHashMap<>();


    @PostConstruct
    private void init() throws Exception {

        this.requestConfig = RequestConfig.custom()
                // 读取数据的超时时间
                .setSocketTimeout(20000)
                // 连接服务器的超时时间
                .setConnectTimeout(5000)
                // 从连接池中获取连接的超时时间
                .setConnectionRequestTimeout(5000).build();
        SSLContextBuilder builder = new SSLContextBuilder();

        builder.loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        });

        builder.setProtocol("TLSv1.2");

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(builder.build(), new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory()).register("https", socketFactory).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(20);
        connectionManager.setDefaultMaxPerRoute(4);

        this.httpClientBuilder = HttpClients.custom();
        this.httpClientBuilder.setConnectionManager(connectionManager);
        this.httpClientBuilder.setSSLSocketFactory(socketFactory);
        this.httpClientBuilder.setMaxConnTotal(20);
        this.httpClientBuilder.disableAutomaticRetries();
        this.httpClientBuilder.evictIdleConnections(3, TimeUnit.SECONDS);

        // 初始化回调地址
        this.initCallbackUrl();

        // 节点可用性线程启动
        this.initCheckNodeThread();
    }

    private void initCallbackUrl() {

        List<CallbackConfig.Callback> targets = callbackConfig.getDestList();

        if (CollectionUtils.isNotEmpty(targets)) {

            for (CallbackConfig.Callback callback : targets) {

                String project = callback.getProject();
                String[] hosts = callback.getUrl().split(",");

                CallBackNode callBackNode = new CallBackNode();

                for (String host : hosts) {

                    String callbackUrl = host + "/" + project + "/callback/notify";

                    boolean result = this.checkNodeAvailable(callbackUrl, this.getClient(callbackUrl));

                    if (result) {

                        callBackNode.addCallbackUrl(callbackUrl);
                    } else {

                        callBackNode.addNoneCallbackUrl(callbackUrl);
                    }
                }

                callBackNode.resetAvailableNode();

                this.callBackNodeMap.put(project, callBackNode);

            }
        }
    }

    private void initCheckNodeThread() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                log.info("线程可用性检测启动");

                while (true) {

                    for (String key : callBackNodeMap.keySet()) {

                        CallBackNode callBackNode = callBackNodeMap.get(key);

                        if (CollectionUtils.isNotEmpty(callBackNode.getUnavailableList())) {

                            for (String callbackUrl : callBackNode.getUnavailableList()) {

                                boolean result = checkNodeAvailable(callbackUrl, getClient(callbackUrl));

                                if (result) {

                                    callBackNode.switchAvailableNode(callbackUrl);
                                }
                            }
                        }
                    }

                    try {

                        TimeUnit.MINUTES.sleep(5);

                    } catch (InterruptedException e) {

                        log.error("线程中断:{}", e.getMessage(), e);
                    }
                }
            }
        });

        thread.setName("CHECK_NODE_THREAD");
        thread.setDaemon(true);
        thread.start();

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {

                if ("CHECK_NODE_THREAD".equals(t.getName())) {

                    initCheckNodeThread();
                }
            }
        });
    }


    /**
     * 回调节点检测可用性
     *
     * @param callbackUrl 回调地址
     * @param client      客户端
     * @return
     */
    private boolean checkNodeAvailable(String callbackUrl, CloseableHttpClient client) {

        boolean result = false;
        CloseableHttpResponse response = null;

        try {

            HttpPost post = new HttpPost(callbackUrl);

            post.setConfig(this.requestConfig);
            post.setEntity(new UrlEncodedFormEntity(
                    Collections.singletonList(new BasicNameValuePair("msg", "ping")), "UTF-8"
            ));

            response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = true;
                log.info("同步消息回调节点检测成功:{} ", callbackUrl);
            }else {
                log.warn("回调检测失败:{},callbackUrl:{}",EntityUtils.toString(response.getEntity()), callbackUrl);
            }

        } catch (Exception e) {

            log.warn("回调检测失败:{},callbackUrl:{}", e.getMessage(), callbackUrl);

        } finally {

            if (null != response) {

                try {
                    response.close();

                } catch (IOException e) {

                    log.error("关闭响应流失败:{}", e.getMessage(), e);
                }
            }

        }

        return result;
    }

    /**
     * 失败次数超过3次切换节点为不可用
     *
     * @param callback    回调任务
     * @param callbackUrl 回调url
     */
    private void checkSwitchNode(CallbackTask callback, String callbackUrl) {

        if(StringUtils.isBlank(callbackUrl)){
            return;
        }

        if (!this.unFailMap.containsKey(callbackUrl)) {

            this.unFailMap.put(callbackUrl, new AtomicInteger(0));
        }

        int failCount = this.unFailMap.get(callbackUrl).incrementAndGet();

        if (failCount > 3) {
            this.callBackNodeMap.get(callback.getProject()).switchUnAvailableNode(callbackUrl);
            this.unFailMap.put(callbackUrl, new AtomicInteger(0));
        }

    }

    /**
     * 获取http客户端
     *
     * @param url 回调地址
     * @return
     */
    public CloseableHttpClient getClient(String url) {

        if (!this.httpClientMap.containsKey(url)) {

            this.httpClientMap.put(url, this.httpClientBuilder.build());
        }

        return this.httpClientMap.get(url);
    }

    /**
     * 加入回调任务队列
     *
     * @param callbackTask 回调任务
     */
    public void addTask(final CallbackTask callbackTask) {

        threadPoolExecutor.execute(() -> {

            try {

                boolean result = dealWithTask(callbackTask);
                if (!result) {
                    CallbackContext.getCallbackContext().addTaskToDelayQueue(callbackTask);
                }

            } catch (Throwable throwable) {

                log.error("回调失败", throwable);
            }
        });
    }

    /**
     * 处理回调任务
     *
     * @param callback 回调任务
     * @return
     * @throws Throwable
     */
    private boolean dealWithTask(CallbackTask callback) throws Throwable {

        // 标记回调开始
        if (!this.markCallbackStart(callback)) {

            return true;
        }

        // 开始回调
        ResultVO<Void> result = this.sendPost(callback);

        // 标记回调结果
        this.markCallbackEnd(callback, result);

        return ResultConstant.RESULT_CODE_200.equals(result.getCode());
    }

    /**
     * 标记回调完成
     *
     * @param callback 回调任务
     * @param result  回调结果
     * @throws Throwable
     */
    public void markCallbackEnd(CallbackTask callback, ResultVO<Void> result) throws Throwable {

        AbstractExecutor executor = AbstractExecutor.build(callback.getDb());

        UpdateBuilder updateBuilder = UpdateBuilder.create(SyncContext.getContext().getDbType(callback.getDb()));
        updateBuilder.table("pub_callback").column("status").column("ack_time").column("error_msg");
        updateBuilder.whereIn("uuid", 1);

        // 重试次数+1
        updateBuilder.inc("repeat_count", 1);

        List<Object> paramList = new ArrayList<>();

        if (ResultConstant.RESULT_CODE_200.equals(result.getCode())) {
            paramList.add(PlanConstant.CALLBACK_STATUS_SUCCESS);
        } else {
            paramList.add(PlanConstant.CALLBACK_STATUS_FAIL);
        }

        paramList.add(new Date());
        paramList.add(result.getMsg());
        paramList.add(callback.getUuid());

        SqlMeta sqlMeta = new SqlMeta();
        sqlMeta.setSqlType(SyncConstant.OPERATE_TYPE_UPDATE);
        sqlMeta.setSql(updateBuilder.sql());
        sqlMeta.setParamList(SyncConvert.toArrayList(paramList));

        executor.sql(sqlMeta);
        executor.execute();

    }

    /**
     * 标记回调进行中
     *
     * @param callback 回调任务
     * @return
     * @throws Throwable
     */
    public boolean markCallbackStart(CallbackTask callback) throws Throwable {

        boolean success = false;

        AbstractExecutor executor = AbstractExecutor.build(callback.getDb());

        boolean isMongoDB = executor.getMongoTemplate() != null;

        Object callbackId = null;

        if (!isMongoDB) {

            SelectBuilder selectBuilder = SelectBuilder.create(SyncContext.getContext().getDbType(callback.getDb()));
            selectBuilder.table("pub_callback").column("callback_id");
            selectBuilder.whereEq("uuid");

            SqlMeta sqlMeta = new SqlMeta();
            sqlMeta.setSql(selectBuilder.sql());
            sqlMeta.setParamList(SyncConvert.toArrayList(Collections.singletonList(callback.getUuid())));
            sqlMeta.setSqlType(SyncConstant.OPERATE_TYPE_SELECT);

            executor.sql(sqlMeta);

            sqlMeta.setSql(SqlUtil.fillParam(sqlMeta, executor));
            List<SqlMeta> sqlMetaList = executor.execute();
            if (sqlMetaList != null && sqlMetaList.size() == 1 && sqlMetaList.get(0).getResultList() != null && !sqlMetaList.get(0).getResultList().isEmpty()) {

                callbackId = sqlMetaList.get(0).getResultList().get(0).get("callback_id");

            } else {

                return false;
            }

            executor.clear();

        }

        UpdateBuilder updateBuilder = UpdateBuilder.create(SyncContext.getContext().getDbType(callback.getDb()));
        updateBuilder.table("pub_callback").column("status").column("send_time");

        List<Object> paramList = new ArrayList<>();
        paramList.add(PlanConstant.CALLBACK_STATUS_ING);
        paramList.add(new Date());

        if (!isMongoDB) {

            updateBuilder.whereIn("callback_id", 1).whereIn("status", 1);
            paramList.add(callbackId);
        } else {
            updateBuilder.whereIn("uuid", 1).whereIn("status", 1);
            paramList.add(callback.getUuid());

        }

        if (callback.isFirst()) {

            paramList.add(PlanConstant.CALLBACK_STATUS_WAIT);
        } else {

            paramList.add(PlanConstant.CALLBACK_STATUS_FAIL);
        }

        SqlMeta sqlMeta = new SqlMeta();
        sqlMeta.setSql(updateBuilder.sql());
        sqlMeta.setParamList(SyncConvert.toArrayList(paramList));
        sqlMeta.setSqlType(SyncConstant.OPERATE_TYPE_UPDATE);

        executor.sql(sqlMeta);
        sqlMeta.setSql(SqlUtil.fillParam(sqlMeta, executor));
        List<SqlMeta> metaList = executor.execute();
        if (metaList.size() == 1 && metaList.get(0).getAffectNum() == 1) {

            success = true;
        }

        return success;

    }

    /**
     * 发送回调请求
     *
     * @param callback 回调任务
     * @return
     */
    private ResultVO<Void> sendPost(CallbackTask callback) {

        boolean result = false;
        String error = StringUtils.EMPTY;

        CloseableHttpClient client;
        CloseableHttpResponse response = null;
        String callbackUrl = null;

        try {

            CallBackNode callBackNode = this.callBackNodeMap.get(callback.getProject());

            if (callBackNode == null) {

                throw new RuntimeException(callback.getProject() + " 没有配置回调主机地址");
            }

            callbackUrl = callBackNode.getCallbackUrl();

            if (StringUtils.isBlank(callbackUrl)) {

                throw new RuntimeException(callback.getProject() + " 没有可用的回调主机地址");
            }

            client = this.getClient(callbackUrl);

            // 回调接口调用
            HttpPost post = new HttpPost(callbackUrl);

            String msg = callback.getMsg();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String nonce = RandomStringUtils.randomAlphanumeric(32);
            String encrypt = DesUtil.encrypt((msg + nonce + timestamp), System.getProperty("secret.key"));
            // 接口签名
            post.setHeader("timestamp", timestamp);
            post.setHeader("nonce", nonce);
            post.setHeader("sign", encrypt);
            post.setConfig(this.requestConfig);
            post.setEntity(new UrlEncodedFormEntity(Collections.singletonList(new BasicNameValuePair("msg", msg)), "UTF-8"));

            response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                String resultStr = EntityUtils.toString(response.getEntity());

                if (StringUtils.isNotBlank(resultStr)) {

                    JSONObject jsonObject = JSONObject.parseObject(resultStr);
                    if (jsonObject != null && jsonObject.containsKey("code") && HttpStatus.SC_OK == jsonObject.getInteger("code")) {
                        result = true;
                    } else {
                        error = resultStr;
                    }
                }
            } else {
                error = response.getStatusLine().toString();
            }

        } catch (Exception e) {

            if (e instanceof HttpHostConnectException) {

                error = "无法连接主机";

            } else if (e instanceof ConnectTimeoutException) {

                error = "网络连接超时";
            } else {

                error = "未知异常: " + ExceptionUtils.getRootCauseMessage(e);
            }

            this.checkSwitchNode(callback, callbackUrl);

        } finally {

            if (null != response) {

                try {

                    response.close();

                } catch (IOException e) {

                    log.error("关闭响应流失败:{}", e.getMessage(), e);
                }
            }

        }

        return result ? ResultUtil.getSuccess() : ResultUtil.getFail(error);
    }

    @PreDestroy
    private void destroy() {
        this.threadPoolExecutor.shutdown();
    }
}
