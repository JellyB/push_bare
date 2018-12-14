package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.SuggestFeedbackInfo;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-14 下午1:26
 **/
public interface SuggestFeedbackService {

    /**
     * 处理单个纠错消息通知
     * @param suggestFeedbackInfo
     * @throws BizException
     */
    void sendSuggestNotice(SuggestFeedbackInfo suggestFeedbackInfo) throws BizException;
}
