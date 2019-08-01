package com.huatu.tiku.push.listener.amq;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.CorrectReturnInfo;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.service.api.CorrectReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：申论批改监听队列
 *
 * @author biguodong
 * Create time 2018-12-11 下午1:51
 **/
@Component
@RabbitListener(queues = RabbitMqKey.NOTICE_CORRECT_RETURN)
@Slf4j
public class CorrectReturnListener {

    @Autowired
    private CorrectReturnService correctReturnService;


    @RabbitHandler
    public void onMessage(String message){
        CorrectReturnInfo correctInfo = JSONObject.parseObject(message, CorrectReturnInfo.class);
        log.info("申论批改被退回内容:{}", JSONObject.toJSONString(correctInfo));
        correctReturnService.sendCorrectNotice(correctInfo);
    }

}
