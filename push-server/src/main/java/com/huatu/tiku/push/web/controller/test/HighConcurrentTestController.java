package com.huatu.tiku.push.web.controller.test;

import com.alibaba.fastjson.JSONObject;
import com.huatu.springboot.web.version.mapping.annotation.ApiVersion;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import com.huatu.tiku.push.enums.NoticeReadEnum;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.concurrent.atomic.LongAdder;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-29 下午6:01
 **/

@RestController
@RequestMapping(value = "highConcurrent")
@ApiVersion(value = "v1")
@Slf4j
public class HighConcurrentTestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping(value = "HikariCp")
    public void testConnectionException(){
        final long fixCount = 50000;
        final long noticeId = 10008611;
        LongAdder longAdder = new LongAdder();

        while(true){
            if(longAdder.longValue() > fixCount){
                break;
            }
            longAdder.increment();
            NoticeUserRelation noticeUserRelation = NoticeUserRelation.builder()
                    .noticeId(noticeId)
                    .userId(longAdder.longValue())
                    .type("HighConcurrence")
                    .detailType("HikariCP")
                    .createTime(new Timestamp(System.currentTimeMillis()))
                    .updateTime(new Timestamp(System.currentTimeMillis()))
                    .status(NoticeStatusEnum.NORMAL.getValue())
                    .isRead(NoticeReadEnum.UN_READ.getValue())
                    .build();
            String message = JSONObject.toJSONString(noticeUserRelation);
            rabbitTemplate.convertAndSend(RabbitMqKey.NOTICE_USER_LANDING_HIKARICP_TEST, message);
        }
    }
}
