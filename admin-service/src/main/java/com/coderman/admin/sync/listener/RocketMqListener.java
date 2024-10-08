package com.coderman.admin.sync.listener;

import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.service.ResultService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RocketMqListener implements MessageListenerConcurrently {

    private final static Logger logger = LoggerFactory.getLogger(RocketMqListener.class);

    private ResultService resultService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {

        int retryTimeLimit = 8;

        try {

            if (this.resultService.successMsgExistRedis(messageExtList.get(0).getMsgId())) {
                logger.error("consumeMessage-重复消息,标记成功:" + messageExtList.get(0).getMsgId());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

        } catch (Exception e) {

            logger.error("consumeMessage-exception:" + e.getMessage());
        }


        for (MessageExt message : messageExtList) {

            // 获取消息
            String msg;

            try {

                if (message.getReconsumeTimes() > retryTimeLimit) {

                    logger.error("投送次数超过:" + retryTimeLimit + "次,不处理当前消息,当前" + message.getReconsumeTimes() + "次,msgID:" + message.getMsgId());
                    continue;
                }

                msg = new String(message.getBody(), StandardCharsets.UTF_8);

            } catch (Exception e) {

                logger.error("MQ消息解码失败:{}", e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }


            try {

                String result = SyncContext.getContext().syncData(msg, message.getMsgId(), SyncConstant.MSG_ROCKET_MQ, message.getReconsumeTimes());

                if (!SyncConstant.SYNC_END.equalsIgnoreCase(result)) {

                    logger.error("RocketMqListener同步结果:{}", result);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

            } catch (Exception e) {

                logger.error("消息处理异常:{}", e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

        }

        // 如果没有异常都任务消费成功
        this.resultService.successMsgSave2Redis(messageExtList.get(0).getMsgId(), 60);

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}

