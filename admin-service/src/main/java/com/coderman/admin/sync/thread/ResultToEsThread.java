package com.coderman.admin.sync.thread;

import com.coderman.admin.sync.model.ResultModel;
import com.coderman.admin.sync.es.EsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyukang
 */
@Component
@Slf4j
@Lazy(false)
public class ResultToEsThread {


    private static final Queue<ResultModel> QUEUE = new ConcurrentLinkedQueue<>();

    private static volatile boolean loop = true;

    @Resource
    private EsService esService;


    public void add(ResultModel resultModel) {
        QUEUE.add(resultModel);
    }

    @PostConstruct
    public void init() {

        Thread thread = new Thread(() -> {

            log.info("同步ES线程启动");

            List<ResultModel> list = new ArrayList<>();

            while (loop) {

                try {

                    insertBatchRecord(list);

                    if (CollectionUtils.isNotEmpty(list)) {

                        esService.batchInsertSyncResult(list);
                    }

                    TimeUnit.SECONDS.sleep(3);
                    log.debug("同步记录刷新到ES， 等待中...");

                } catch (Exception e) {

                    log.error("同步记录到ES失败:{}", e.getMessage(), e);

                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException ignored) {
                    }

                }finally {

                    list.clear();
                }
            }
        });

        thread.setDaemon(true);
        thread.setName("RESULT_TO_ES_THREAD");
        thread.start();

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {

                if ("RESULT_TO_ES_THREAD".equals(t.getName())) {

                    init();
                }
            }
        });
    }

    private void insertBatchRecord(List<ResultModel> list) throws IOException {

        ResultModel resultModel;

        while (null != (resultModel = QUEUE.poll())) {

            list.add(resultModel);

            if (list.size() >= 50) {

                this.esService.batchInsertSyncResult(list);

                list.clear();
            }
        }
    }

    @PreDestroy
    private void flushInfo() throws Exception {

        loop = false;

        Thread.sleep(1200);

        List<ResultModel> list =  new ArrayList<>();

        this.insertBatchRecord(list);

        if(CollectionUtils.isNotEmpty(list)){

            this.esService.batchInsertSyncResult(list);
        }
    }
}
