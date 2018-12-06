package com.huatu.tiku.push.listener.amq;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.entity.SimpleUser;
import com.huatu.tiku.push.manager.SimpleUserManager;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-20 下午5:51
 **/

@Component
@RabbitListener(queues = RabbitMqKey.NOTICE_USER_STORING)
public class UserStoringListener {

    @Autowired
    private SimpleUserManager simpleUserManager;

    @RabbitHandler
    public void onMessage(String message){
        SimpleUser simpleUser = JSONObject.parseObject(message, SimpleUser.class);
        simpleUserManager.saveSimpleUser(simpleUser);
    }
}
