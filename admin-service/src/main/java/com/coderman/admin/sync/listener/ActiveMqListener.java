package com.coderman.admin.sync.listener;

import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.service.ResultService;
import com.coderman.service.util.SpringContextUtil;
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
@Slf4j
public class ActiveMqListener implements SessionAwareMessageListener<TextMessage> {


    @Override
    public void onMessage(@NonNull TextMessage message, @NonNull Session session) throws JMSException {

        try {

            ResultService resultService = SpringContextUtil.getBean(ResultService.class);

            int retryTimeLimit = 6;
            int deliveryCount = message.getIntProperty("JMSXDeliveryCount");
            String messageId = message.getJMSMessageID();


            if(StringUtils.equals(message.getText() , "success")){
                log.info("consumeMessage-消费成功:{}", messageId);
                message.acknowledge();
                return;
            }

            if (resultService.successMsgExistRedis(messageId)) {
                log.warn("consumeMessage-重复消息,标记成功:{}", messageId);
                message.acknowledge();
                return;
            }

            if (deliveryCount > retryTimeLimit) {
                log.warn("投送次数超过:{}次,不处理当前消息,当前{}次,msgID:{}", retryTimeLimit, deliveryCount, messageId);
                message.acknowledge();
                return;
            }

            // 获取消息
            String result = SyncContext.getContext().syncData(new String(message.getText().getBytes(), StandardCharsets.UTF_8), messageId, SyncConstant.MSG_ACTIVE_MQ, deliveryCount);
            if (!SyncConstant.SYNC_END.equalsIgnoreCase(result)) {

                throw new RuntimeException(String.format("ActiveMqListener同步结果:[%s], 当前重试次数:[%s],消息id:[%s]", result, deliveryCount, messageId));
            }

            // 手动ack
            message.acknowledge();

            // 如果没有异常都任务消费成功
            resultService.successMsgSave2Redis(messageId, 60);

        } catch (Exception e) {

            log.error("ActiveMQ消息者监听器消费失败：{}", e.getMessage());
            session.recover();
        }
    }
}
