package com.coderman.admin.service.common;

import com.coderman.admin.dto.common.ChatGptDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatService {

    /**
     * ai对话
     * @param chatGptDTO
     * @param sseEmitter
     */
    void completion(ChatGptDTO chatGptDTO, SseEmitter sseEmitter);
}
