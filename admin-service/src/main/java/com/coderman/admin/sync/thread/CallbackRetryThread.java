package com.coderman.admin.sync.thread;

import com.coderman.admin.sync.callback.CallbackContext;
import com.coderman.admin.sync.callback.meta.CallbackTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.DelayQueue;

/**
 * @author zhangyukang
 */
@Component
@Lazy(false)
@Slf4j
public class CallbackRetryThread {


    private final DelayQueue<CallbackTask> queue = new DelayQueue<>();


    public void add(CallbackTask callbackTask){

        this.queue.add(callbackTask);
    }

    @PostConstruct
    public void init(){

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                log.info("回调重试线程启动");

                while (true){

                    try {

                        CallbackTask callbackTask = queue.take();

                        callbackTask.setFirst(false);

                        CallbackContext.getCallbackContext().addTask(callbackTask);

                    } catch (InterruptedException e) {

                        throw new RuntimeException(e);
                    }

                }

            }

        });

        thread.setName("CALLBACK_RETRY_THREAD");
        thread.setDaemon(true);
        thread.start();

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {

                if("CALLBACK_RETRY_THREAD".equals(t.getName())){

                    init();
                }
            }
        });
    }

}
