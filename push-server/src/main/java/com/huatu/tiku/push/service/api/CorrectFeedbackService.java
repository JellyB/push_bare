package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 下午6:13
 **/
public interface CorrectFeedbackService {

    /**
     * 批量处理纠错消息通知
     * @param correctFeedbackInfoList
     * @throws BizException
     */
    void sendCorrectNotice(List<CorrectFeedbackInfo> correctFeedbackInfoList) throws BizException;

    /**
     * 处理单个纠错消息通知
     * @param correctFeedbackInfo
     * @throws BizException
     */
    void sendCorrectNotice(CorrectFeedbackInfo correctFeedbackInfo) throws BizException;
}
