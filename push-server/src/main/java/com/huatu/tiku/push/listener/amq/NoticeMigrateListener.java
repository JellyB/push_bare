package com.huatu.tiku.push.listener.amq;

import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.manager.MigrateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：数据转移
 * 废弃
 *
 * @author biguodong
 * Create time 2018-11-13 上午10:26
 **/
@Deprecated
//@Component
//@RabbitListener(queues = RabbitMqKey.NOTICE_DATA_MOVE_FROM_PANDORA_2_PUSH)
@Slf4j
public class NoticeMigrateListener {

    @Autowired
    private MigrateManager migrateManager;

    //@RabbitHandler
    public void onMessage(String message){
        //return;
        log.info(message);
        migrateManager.insertRelation(message);
    }
}
