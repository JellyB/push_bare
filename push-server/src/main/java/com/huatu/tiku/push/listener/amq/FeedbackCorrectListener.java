package com.huatu.tiku.push.listener.amq;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.service.api.CorrectFeedbackService;
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
@RabbitListener(queues = RabbitMqKey.NOTICE_FEEDBACK_CORRECT)
@Slf4j
public class FeedbackCorrectListener {

    @Autowired
    private CorrectFeedbackService correctFeedbackService;


    @RabbitHandler
    public void onMessage(String message){
        CorrectFeedbackInfo correctFeedbackInfo = JSONObject.parseObject(message, CorrectFeedbackInfo.class);
        log.info("新的纠错反馈信息:{}", JSONObject.toJSONString(correctFeedbackInfo));
        correctFeedbackService.sendCorrectNotice(correctFeedbackInfo);
    }

}
