package com.coderman.admin.sync.config;

import com.coderman.admin.jobhandler.TianTianFundHandler;
import com.coderman.admin.sync.jobhandler.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@EnableScheduling
@Configuration
@Component
@ConditionalOnProperty(prefix = "job",name = "enable",havingValue = "false")
public class SchedulingConfig {

    @Resource
    private RepeatSyncHandler repeatSyncHandler;
    @Resource
    private CleanMessageHandler cleanMessageHandler;
    @Resource
    private CleanResultHandler cleanResultHandler;
    @Resource
    private ResultToEsHandler resultToEsHandler;
    @Resource
    private UpdateSuccessHandler updateSuccessHandler;
    @Resource
    private CallbackHandler callbackHandler;
    @Resource
    private TianTianFundHandler tianTianFundHandler;

    @Scheduled(cron = "*/10 * * * * ?")
    public void tianTianFundHandler(){

        this.tianTianFundHandler.execute(StringUtils.EMPTY);
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void repeatSyncHandler() {

        this.repeatSyncHandler.execute(StringUtils.EMPTY);
    }

    @Scheduled(cron = "0 */30 * * * ?")
    public void cleanMessageHandler() {

        this.cleanMessageHandler.execute(StringUtils.EMPTY);
    }

    @Scheduled(cron = "0 */30 * * * ?")
    public void cleanResultHandler() {

        this.cleanResultHandler.execute(StringUtils.EMPTY);
    }


    @Scheduled(cron = "0 */5 * * * ?")
    public void resultToEsHandler() {

        this.resultToEsHandler.execute(StringUtils.EMPTY);
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void updateSuccessHandler() throws Exception {

        this.updateSuccessHandler.execute(StringUtils.EMPTY);
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void callbackHandler() {

        this.callbackHandler.execute(StringUtils.EMPTY);
    }


}
