package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import com.huatu.tiku.push.enums.NoticeReadEnum;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.concurrent.atomic.LongAdder;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-29 下午5:50
 **/
public class RabbitMqHikariConnectTimeOutExceptionTest extends PushBaseTest{



    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testConnectionException(){
        final long noticeId = 10008612;
        final long total = 200000;
        LongAdder longAdder = new LongAdder();

        while(true){
            longAdder.increment();
            if(longAdder.longValue() > total ){
                break;
            }
            NoticeUserRelation noticeUserRelation = NoticeUserRelation.builder()
                    .noticeId(noticeId)
                    .userId(longAdder.longValue())
                    .type("HighConcurrenceTest")
                    .detailType("HighConcurrenceTestHikariCP")
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
