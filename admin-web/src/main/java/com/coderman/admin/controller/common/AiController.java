package com.coderman.admin.controller.common;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.coderman.service.util.DesUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2025/03/21 17:07
 */
@Api(value = "AI接口", tags = "AI接口")
@RestController
@Slf4j
@RequestMapping(value = "/common/ai")
public class AiController {

    @GetMapping("/chat")
    public Object chat(@RequestParam(value = "question", required = false) String question) throws Exception {
        return callWithMessage(question);
    }

    public String callWithMessage(String question)
            throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        List<Message> messages = new ArrayList<>();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(question).build();
        messages.add(userMsg);
        String crypyKey = System.getProperty("secret.key");
        String apiKey = DesUtil.decrypt("345D37C0A831EA3BF82562BE4758A70285A77A4AB8E0685299C5246B3DD67F3F74843A274F180E0C", crypyKey);
        GenerationParam param =
                GenerationParam.builder().model(Generation.Models.QWEN_TURBO).messages(messages)
                        .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                        .apiKey(apiKey)
                        .build();
        GenerationResult result = gen.call(param);
        return result.getOutput().getChoices().get(0).getMessage().getContent();
    }

}
