package com.coderman.admin.service.common;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * @author ：zhangyukang
 * @date ：2025/04/02 11:14
 */
public interface AssistantService {

    @SystemMessage(value = {"你是一个智能助手，专门帮助用户解答问题，提供服务和支持。"})
    TokenStream completion(@MemoryId String sessionId, @UserMessage String userMessage);
}
