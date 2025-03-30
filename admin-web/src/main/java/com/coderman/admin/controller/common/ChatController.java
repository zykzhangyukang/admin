package com.coderman.admin.controller.common;

import com.coderman.admin.dto.common.ChatGptDTO;
import com.coderman.admin.service.common.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;

/**
 * @author ：zhangyukang
 * @date ：2025/03/21 17:07
 */
@Api(value = "AI接口", tags = "AI接口")
@RestController
@Slf4j
@RequestMapping(value = "/common/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    @ApiModelProperty(value = "AI对话")
    @GetMapping(value = "/completion",produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter completion(ChatGptDTO chatGptDTO) {
        SseEmitter sseEmitter = new SseEmitter(5 * 60 * 1000L);
        this.chatService.completion(chatGptDTO, sseEmitter);
        sseEmitter.onTimeout(() -> {
            log.warn("SSE连接超时");
            sseEmitter.complete();
        });

        sseEmitter.onError(e -> {
            log.error("SSE连接错误", e);
            sseEmitter.complete();
        });
        return sseEmitter;
    }


}
