package com.coderman.admin.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.coderman.admin.dto.common.ChatGptDTO;
import com.coderman.admin.service.common.Assistant;
import com.coderman.admin.service.common.ChatService;
import dev.langchain4j.service.TokenStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
@Configuration
public class ChatServiceImpl implements ChatService {

    @Resource
    private Assistant assistant;

    /**
     * 调用大模型接口
     * @param chatGptDTO 参数
     * @param sseEmitter sse
     */
    public void completion(ChatGptDTO chatGptDTO, SseEmitter sseEmitter) {
        try {
            TokenStream stream = assistant.completion(chatGptDTO.getSessionId(), chatGptDTO.getPrompt());
            // 监听回调
            stream.onNext(result -> sendSseData(sseEmitter, result));
            stream.onError(throwable -> handleError(sseEmitter, throwable));
            stream.onComplete(response -> completeSse(sseEmitter));
            stream.start();
        } catch (Exception e) {
            throw new RuntimeException("completion error:"+e.getMessage());
        }
    }

    private void sendSseData(SseEmitter sseEmitter, String result) {
        try {
            JSONObject jsonObject =  new JSONObject();
            jsonObject.put("text",result);
            sseEmitter.send(jsonObject, MediaType.APPLICATION_JSON);
        } catch (IOException e) {
            log.warn("SSE 发送数据失败:{}", e.getMessage());
        }
    }

    private void handleError(SseEmitter sseEmitter, Throwable throwable) {
        log.error("SSE 发生错误:{}", throwable.getMessage());
        sseEmitter.completeWithError(throwable);
    }

    private void completeSse(SseEmitter sseEmitter) {
        try {
            sseEmitter.send("[DONE]");
            sseEmitter.complete();
        } catch (Exception e) {
            log.warn("SSE 关闭失败:{}", e.getMessage());
        }
    }
}
