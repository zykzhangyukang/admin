package com.coderman.bizedu.sync.listener;

import com.coderman.bizedu.constant.SyncConstant;
import com.coderman.bizedu.sync.context.SyncContext;
import com.coderman.bizedu.sync.service.result.ResultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.nio.charset.StandardCharsets;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class ActiveMqListener implements SessionAwareMessageListener<TextMessage> {

    @Resource
    private ResultService resultService;

    @Override
    public void onMessage(@NonNull TextMessage message, @NonNull Session session) throws JMSException {

        try {

            int retryTimeLimit = 8;
            int deliveryCount = message.getIntProperty("JMSXDeliveryCount");
            String messageId = message.getJMSMessageID();


            if(StringUtils.equals(message.getText() , "success")){
                log.info("consumeMessage-消费成功:" + messageId);
                message.acknowledge();
                return;
            }

            if (resultService.successMsgExistRedis(messageId)) {
                log.warn("consumeMessage-重复消息,标记成功:" + messageId);
                message.acknowledge();
                return;
            }

            if (deliveryCount > retryTimeLimit) {
                log.warn("投送次数超过:" + retryTimeLimit + "次,不处理当前消息,当前" + deliveryCount + "次,msgID:" + messageId);
                message.acknowledge();
                return;
            }

            // 获取消息
            String result = SyncContext.getContext().syncData(new String(message.getText().getBytes(), StandardCharsets.UTF_8), messageId, SyncConstant.MSG_ACTIVE_MQ, deliveryCount);
            if (!SyncConstant.SYNC_END.equalsIgnoreCase(result)) {

                throw new RuntimeException(String.format("RocketMqListener同步结果:[%s], JMSXDeliveryCount:[%s],JMSMessageID:[%s]", result, deliveryCount, messageId));
            }

            // 手动ack
            message.acknowledge();

            // 如果没有异常都任务消费成功
            this.resultService.successMsgSave2Redis(messageId, 60);

        } catch (Exception e) {

            log.error("消息者监听器消费失败：{}", e.getMessage());
            session.recover();
        }
    }
}
