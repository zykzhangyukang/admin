package com.coderman.bizedu.server;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ：zhangyukang
 * @date ：2023/11/06 11:12
 */
@Component
@Lazy(value = false)
public class ActiveMQServer implements SmartLifecycle {

    private final static Logger logger = LoggerFactory.getLogger(ActiveMQServer.class);

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    @Override
    public void start() {

        if (this.initialized.compareAndSet(false, true)) {

            try {

                BrokerService brokerService = new BrokerService();

                //设置是否应将代理的服务公开到jmx中。默认是 true
                brokerService.setUseJmx(true);

                //为指定地址添加新的传输连接器
                brokerService.addConnector("tcp://localhost:61616");

                // 启动服务
                brokerService.start();

                logger.info("启动内嵌 ActiveMQ 服务器完成......");
            } catch (Exception e) {

                logger.error("启动内嵌 ActiveMQ 服务器失败...", e);
            }
        }
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop() {
        this.initialized.getAndSet(false);
    }

    @Override
    public void stop(Runnable callback) {
        callback.run();
    }

    @Override
    public boolean isRunning() {
        return this.initialized.get();
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
