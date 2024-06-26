package com.coderman.admin.sync.callback;

import com.alibaba.fastjson.JSON;
import com.coderman.service.util.SpringContextUtil;
import com.coderman.admin.sync.callback.meta.CallbackTask;
import com.coderman.admin.sync.thread.CallbackRetryThread;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyukang
 */
@Component
@Lazy(false)
@Slf4j
public class CallbackContext {

    @Getter
    private static CallbackContext callbackContext;

    @Resource
    private CallBackExecutor callBackExecutor;

    @Resource
    private CallbackRetryThread callbackRetryThread;


    @PostConstruct
    private void init() {

        CallbackContext.callbackContext = SpringContextUtil.getBean(CallbackContext.class);
    }

    public static void setCallbackContext(CallbackContext callbackContext) {
        CallbackContext.callbackContext = callbackContext;
    }

    public void addTask(List<CallbackTask> callbackTaskList) {

        for (CallbackTask callbackTask : callbackTaskList) {

            this.addTask(callbackTask);
        }
    }

    public void addTask(CallbackTask callbackTask) {


        this.callBackExecutor.addTask(callbackTask);
    }

    public void addTaskToDelayQueue(CallbackTask callbackTask) {

        int retryTimes = callbackTask.getRetry().getAndIncrement();

        if (retryTimes == 0) {

            callbackTask.setDelayTime(10);

        } else if (retryTimes == 1) {

            callbackTask.setDelayTime(30);

        } else if (retryTimes == 2) {

            callbackTask.setDelayTime(60);

        } else {

            log.error("超过重试次数 3 次,禁止重试，callbackTask:{}", JSON.toJSONString(callbackTask));
            return;
        }

        this.callbackRetryThread.add(callbackTask);
    }
}
