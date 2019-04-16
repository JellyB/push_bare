package com.huatu.tiku.push.listener.amq;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.constant.SuggestFeedbackInfo;
import com.huatu.tiku.push.service.api.CorrectFeedbackService;
import com.huatu.tiku.push.service.api.SuggestFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-11 下午1:51
 **/
@Component
@RabbitListener(queues = RabbitMqKey.NOTICE_FEEDBACK_SUGGEST)
@Slf4j
public class FeedbackSuggestListener {

    @Autowired
    private SuggestFeedbackService suggestFeedbackService;


    @RabbitHandler
    public void onMessage(String message){
        SuggestFeedbackInfo suggestFeedbackInfo = JSONObject.parseObject(message, SuggestFeedbackInfo.class);
        log.info("新的建议反馈信息:{}", JSONObject.toJSONString(suggestFeedbackInfo));
        suggestFeedbackService.sendSuggestNotice(suggestFeedbackInfo);
    }

}
