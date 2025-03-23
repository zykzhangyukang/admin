package com.coderman.admin.controller.common;

import com.coderman.admin.utils.ExecuteSSEUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ：zhangyukang
 * @date ：2025/03/21 17:07
 */
@Api(value = "AI接口", tags = "AI接口")
@RestController
@Slf4j
@RequestMapping(value = "/common/chat")
public class ChatController {

    @ApiModelProperty(value = "AI对话")
    @GetMapping("/completion")
    public void chat(@RequestParam(value = "question", required = false) String question, HttpServletResponse response) {
        ExecuteSSEUtil util = new ExecuteSSEUtil();
        util.executeSSE(question,response);
    }


}
