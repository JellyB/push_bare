package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.constant.SuggestFeedbackInfo;
import com.huatu.tiku.push.service.api.SuggestFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-17 下午3:34
 **/
@Slf4j
public class SuggestFeedbackTest extends PushBaseTest{


    @Autowired
    private SuggestFeedbackService suggestFeedbackService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void newNotice(){
        SuggestFeedbackInfo suggestFeedbackInfo = new SuggestFeedbackInfo();
        suggestFeedbackInfo.setBizId(10000123L);
        suggestFeedbackInfo.setUserId(233982024L);
        suggestFeedbackInfo.setCreateTime(System.currentTimeMillis());
        //suggestFeedbackInfo.setSuggestTitle("有个问题");
        suggestFeedbackInfo.setSuggestContent("题目不能正常加载！");
        suggestFeedbackInfo.setReplyTitle("<p></p><br></br>");
        //suggestFeedbackInfo.setReplyContent("<p>大书法家拉设计费水电费静安寺分12342134</p><p>上地附近撒了附近12342134</p><p>上地附近撒了点附近s大神来了否点击爱上了水电费静安寺</p><p><br></p><p>水电费静安寺房间水电费拉升213412341234奥数</p>");
        suggestFeedbackInfo.setReplyContent("<p></p><p></p><p></p><p><br></p><p></p>");
        suggestFeedbackService.sendSuggestNotice(suggestFeedbackInfo);
        //String message = JSONObject.toJSONString(suggestFeedbackInfo);
        //rabbitTemplate.convertAndSend(RabbitMqKey.NOTICE_FEEDBACK_SUGGEST, message);

    }
}
