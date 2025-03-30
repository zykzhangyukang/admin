package com.coderman.admin.service.common.impl;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.coderman.admin.dto.common.ChatGptDTO;
import com.coderman.admin.service.common.ChatService;
import com.coderman.api.constant.CommonConstant;
import com.coderman.service.util.DesUtil;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

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
     *
     * @param chatGptDTO 参数
     * @param sseEmitter sse
     * @throws Exception
     */
    public void callWithMessage(ChatGptDTO chatGptDTO, SseEmitter sseEmitter) throws Exception {

        String prompt = chatGptDTO.getPrompt();
        String sessionId = chatGptDTO.getToken();
        StringBuilder str = new StringBuilder();

        // 构建请求参数
        ApplicationParam param = ApplicationParam.builder()
                .apiKey(DesUtil.decrypt("345D37C0A831EA3BF82562BE4758A70285A77A4AB8E0685299C5246B3DD67F3F74843A274F180E0C", CommonConstant.SECRET_KEY))
                .appId(DesUtil.decrypt("578ACCC33A9F3503E8995759F9EE1CCC4230D1DB6A362CEEF77C0DDBCBA0567C5F2EC4A83FE5B5FD", CommonConstant.SECRET_KEY))
                .prompt(prompt)
                .parameter("question",prompt)
                .sessionId(sessionId)
                .incrementalOutput(true)
                .build();

        Application application = new Application();
        Flowable<ApplicationResult> flowable = application.streamCall(param);
        Disposable subscribe = flowable.subscribe(applicationResult -> {

            sseEmitter.send(applicationResult, MediaType.APPLICATION_JSON);
            str.append(applicationResult.getOutput().getText());
        }, error -> {

            sseEmitter.completeWithError(error);
            log.error("流式调用发生错误:{}", error.getMessage(), error);
        }, () -> {

            sseEmitter.complete();
            log.info(str.toString());
        });
        log.info("subscribe:{}", subscribe);
    }
}
