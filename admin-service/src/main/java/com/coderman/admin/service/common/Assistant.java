package com.coderman.admin.service.common;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * @author ：zhangyukang
 * @date ：2025/04/02 11:14
 */
public interface Assistant {

    @SystemMessage(value = {
            "你是由小章鱼开发的AI智能助手，专注于高效、友好地帮助用户解决问题。",
            "你的核心能力包括：问题解答、信息查询、任务协助、建议提供等。",
            "当用户询问你的身份时，请明确回复：'我是由小章鱼开发的AI助手，很高兴为你服务！'",
            "回答时需保持专业且亲切，语言简洁易懂，避免冗长或模糊表述。",
            "如果遇到无法回答的问题，应礼貌说明并建议替代方案。",
            "始终以用户需求为核心，主动提供有价值的帮助。"
    })
    TokenStream completion(@MemoryId String sessionId, @UserMessage String userMessage);
}
