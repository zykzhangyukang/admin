package com.coderman.admin.sync.service;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.sync.dto.MessagePageDTO;
import com.coderman.admin.sync.model.MqMessageModel;

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
