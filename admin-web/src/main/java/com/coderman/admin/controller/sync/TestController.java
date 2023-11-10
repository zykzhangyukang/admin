package com.coderman.admin.controller.sync;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：zhangyukang
 * @date ：2023/11/08 14:30
 */
@RestController
@RequestMapping(value = "/sync")
public class TestController {

    @Resource
    private JmsTemplate jmsTemplate;

    @GetMapping(value = "/test")
    public ResultVO<Void> test(String message) {
        this.jmsTemplate.convertAndSend(message);
        return ResultUtil.getSuccess();
    }
}
