package com.coderman.admin.sync.service;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.sync.dto.CallbackPageDTO;
import com.coderman.admin.sync.dto.CallbackRepeatDTO;
import com.coderman.admin.sync.model.CallbackModel;

import java.util.List;

public interface CallbackService {

    /**
     * 消息回调列表
     * @param callbackPageDTO 查询参数
     * @return
     */
    ResultVO<PageVO<List<CallbackModel>>> selectCallbackPage(CallbackPageDTO callbackPageDTO);

    /**
     * 重新回调
     *
     * @param callbackRepeatDTO 请求参数
     * @return
     */
    ResultVO<Void> repeatCallback(CallbackRepeatDTO callbackRepeatDTO);
}
