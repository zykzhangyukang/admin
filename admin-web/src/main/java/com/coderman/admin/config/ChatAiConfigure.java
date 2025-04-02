package com.coderman.admin.config;

import com.coderman.admin.service.common.Assistant;
import com.coderman.api.constant.CommonConstant;
import com.coderman.service.util.DesUtil;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author ：zhangyukang
 * @date ：2025/04/02 11:12
 */
@Configuration
@Slf4j
public class ChatAiConfigure {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public Assistant assistant(){
        // 自定义存储方式
        RedisChatMemoryStore redisChatMemoryStore = RedisChatMemoryStore.builder()
                .port(redisProperties.getPort())
                .password(redisProperties.getPassword())
                .host(redisProperties.getHost())
                .build();
        // 构建模型
        StreamingChatLanguageModel model = QwenStreamingChatModel.builder()
                .apiKey(this.getApiKey())
                .modelName("qwen-max")
                .maxTokens(1024)
                .enableSearch(true)
                .build();
        // 构建服务
        return AiServices.builder(Assistant.class)
                .streamingChatLanguageModel(model)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(10)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build())
                .build();
    }

    private String getApiKey() {
        try {
            final String API_KEY_ENCRYPTED = "345D37C0A831EA3BF82562BE4758A70285A77A4AB8E0685299C5246B3DD67F3F74843A274F180E0C";
            return DesUtil.decrypt(API_KEY_ENCRYPTED, CommonConstant.SECRET_KEY);
        } catch (Exception e) {
            log.error("API Key 解密失败", e);
            throw new RuntimeException("无法解密 API Key");
        }
    }

}
