package com.coderman.admin.utils;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.fastjson.JSON;
import com.coderman.api.constant.CommonConstant;
import com.coderman.service.util.DesUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ExecuteSSEUtil {

    private static final String DECRYPTED_API_KEY = DesUtil.decrypt("345D37C0A831EA3BF82562BE4758A70285A77A4AB8E0685299C5246B3DD67F3F74843A274F180E0C", CommonConstant.SECRET_KEY);
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final StringBuilder output = new StringBuilder();
    private PrintWriter writer;

    public void executeSSE(String question, HttpServletResponse response) {
        try {
            // 调用阿里云 GPT
            this.callWithMessage(question, response);
            // 等待流式响应完成
            countDownLatch.await();
        } catch (Exception e) {
            log.error("SSE 执行失败: ", e);
            throw new RuntimeException("SSE 执行失败，请稍后重试", e);
        }
    }

    public void callWithMessage(String question, HttpServletResponse response) throws Exception {
        Generation gen = new Generation();

        // 设置 SSE 响应头
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");

        writer = response.getWriter();

        // 构建消息列表
        List<Message> messages = new ArrayList<>();
        messages.add(Message.builder()
                .role(Role.USER.getValue())
                .content(question)
                .build());

        // 构建请求参数
        GenerationParam param = GenerationParam.builder()
                .model(Generation.Models.QWEN_TURBO)
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .apiKey(DECRYPTED_API_KEY)
                .incrementalOutput(true)
                .build();

        // 发起流式调用
        gen.streamCall(param, new ResultCallback<GenerationResult>() {

            @Override
            public void onEvent(GenerationResult message) {
                if (writer != null) {
                    try {

                        writer.write("data: " + JSON.toJSONString(message.getOutput()) + "\n\n");
                        writer.flush();

                        output.append(message.getOutput().getChoices().get(0).getMessage().getContent());
                    } catch (Exception e) {

                        log.error("写入 SSE 响应失败: ", e);
                        countDownLatch.countDown();
                        throw new RuntimeException("写入 SSE 响应失败", e);
                    }
                }
            }

            @Override
            public void onComplete() {
                try {
                    if (writer != null) {
                        writer.write("data: [DONE]\n\n");
                        writer.flush();
                        writer.close();
                        log.info("SSE 连接完成");
                        log.info("结果输出:{}",output.toString());
                    }
                } catch (Exception e) {
                    log.error("关闭失败: ", e);
                } finally {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void onError(Exception e) {
                try {
                    if (writer != null) {
                        writer.write("event: error\ndata: " + e.getMessage() + "\n\n");
                        writer.flush();
                        writer.close();
                        log.error("SSE 连接出错: ", e);
                    }
                } catch (Exception ex) {
                    log.error("关闭失败: ", ex);
                } finally {
                    countDownLatch.countDown();
                }
            }
        });
    }
}
