package com.huatu.tiku.push.service.api.impl;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.SuggestFeedbackInfo;
import com.huatu.tiku.push.service.api.SuggestFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-14 下午1:27
 **/
@Slf4j
@Service
public class SuggestFeedbackServiceImpl implements SuggestFeedbackService{

    /**
     * 处理单个纠错消息通知
     *
     * @param suggestFeedbackInfo
     * @throws BizException
     */
    @Override
    public void sendSuggestNotice(SuggestFeedbackInfo suggestFeedbackInfo) throws BizException {
        return;
    }
}
