package com.coderman.bizedu.sync.service.message;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.dto.sync.MessagePageDTO;
import com.coderman.bizedu.model.sync.MqMessageModel;

import java.util.List;

public interface MessageService {

    /**
     * 查询本地mq消息
     *
     * @param messagePageDTO 参数dto
     * @return
     */
    ResultVO<PageVO<List<MqMessageModel>>> selectMessagePage(MessagePageDTO messagePageDTO);
}
