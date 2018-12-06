package com.huatu.tiku.push.listener.amq;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import com.huatu.tiku.push.manager.NoticeLandingManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 上午10:26
 **/
@Component
@RabbitListener(queues = RabbitMqKey.NOTICE_PUSH_LANDING)
@Slf4j
public class NoticeLandingListener {

    @Autowired
    private NoticeLandingManager noticeLandingManager;

    @RabbitHandler
    public void onMessage(String message){
        NoticeUserRelation noticeUserRelation = JSONObject.parseObject(message, NoticeUserRelation.class);
        noticeLandingManager.insertNoticeRelation(noticeUserRelation);
    }
}
