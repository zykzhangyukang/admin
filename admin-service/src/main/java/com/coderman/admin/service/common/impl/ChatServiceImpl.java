package com.coderman.admin.service.common.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.coderman.admin.dto.common.ChatGptDTO;
import com.coderman.admin.service.common.ChatService;
import com.coderman.api.constant.CommonConstant;
import com.coderman.api.exception.BusinessException;
import com.coderman.service.util.DesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    /**
     * 接口秘钥
     */
    private static final String API_KEY = "345D37C0A831EA3BF82562BE4758A70285A77A4AB8E0685299C5246B3DD67F3F74843A274F180E0C";

    @Override
    public void completion(ChatGptDTO chatGptDTO, SseEmitter sseEmitter) {
        try {
            this.callWithMessage(chatGptDTO, sseEmitter);
        } catch (Exception e) {
            log.error("SSE 执行失败: ", e);
            throw new RuntimeException("SSE 执行失败，请稍后重试", e);
        }
    }

    /**
     * 调用大模型接口
     * @param chatGptDTO 参数
     * @param sseEmitter sse
     * @throws Exception
     */
    public void callWithMessage(ChatGptDTO chatGptDTO, SseEmitter sseEmitter) throws Exception {

        StringBuilder output = new StringBuilder();
        Generation gen = new Generation();

        String prompt = chatGptDTO.getPrompt();

        List<Message> messages = new ArrayList<>();

        // 系统提示词
        String systemPrompt = "请保持回答简洁明了。如果可能，请直接给出答案而不进行过多解释。";
        messages.add(Message.builder().role(Role.SYSTEM.getValue()).content(systemPrompt).build());

        // 用户消息
        messages.add(Message.builder().role(Role.USER.getValue()).content(prompt).build());

        // 构建请求参数
        GenerationParam param = GenerationParam.builder().model(Generation.Models.QWEN_MAX)
                .maxTokens(1024)
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .apiKey(DesUtil.decrypt(API_KEY, CommonConstant.SECRET_KEY))
                .incrementalOutput(true).build();

        // 发起流式调用
        gen.streamCall(param, new ResultCallback<GenerationResult>() {

            @Override
            public void onEvent(GenerationResult message) {

                try {
                    sseEmitter.send(message.getOutput(), MediaType.APPLICATION_JSON);
                    output.append(message.getOutput().getChoices().get(0).getMessage().getContent());
                } catch (IOException e) {
                    log.error("SSE消息发送失败: {}", e.getMessage(), e);
                    throw new BusinessException("SSE消息发送失败:" + e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                sseEmitter.complete();
                log.info("GPT请求处理完成, 输出内容:{},输出长度: {}", output, output.length());
            }

            @Override
            public void onError(Exception e) {
                log.error("GPT调用发生错误", e);
                sseEmitter.completeWithError(e);
            }
        });
    }
}
