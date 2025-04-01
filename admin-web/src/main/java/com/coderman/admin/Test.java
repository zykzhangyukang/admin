package com.coderman.admin;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;

/**
 * @author ：zhangyukang
 * @date ：2025/04/01 18:09
 */
public class Test {

    /**
     * 定义一个带有单个方法的接口
     */
    interface Assistant {
        String chat(String message);
    }

    public static void main(String[] args) {

        QwenChatModel model = QwenChatModel.builder()
                .apiKey(null)
                .modelName("qwen-max")
                .maxTokens(1000)
                .build();
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemory(chatMemory)
                .build();

        String answer = assistant.chat("你好，我叫小A.");
        System.out.println(answer);

        String answerWithName = assistant.chat("我叫什么名字?");
        System.out.println(answerWithName);
    }

}
