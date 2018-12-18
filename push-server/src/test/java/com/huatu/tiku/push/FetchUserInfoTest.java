package com.huatu.tiku.push;

import com.huatu.tiku.push.constant.CourseQueueEntity;
import com.huatu.tiku.push.constant.RabbitMqKey;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 下午7:54
 **/
public class FetchUserInfoTest extends PushBaseTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void GetUserNamesByClassId(){
        CourseQueueEntity queueEntity1 = CourseQueueEntity.builder()
                .classId(Long.valueOf("86980"))
                .liveId(Long.valueOf("86981"))
                .startTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 6))
                .endTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 16))
                .dealPage(new AtomicInteger(1))
                .build();
        //队列处理user info
        rabbitTemplate.convertAndSend(RabbitMqKey.NOTICE_COURSE_USER_INFO_UNAME, queueEntity1);

        CourseQueueEntity queueEntity2 = CourseQueueEntity.builder()
                .classId(Long.valueOf("86984"))
                .liveId(Long.valueOf("86985"))
                .startTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 6))
                .endTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 16))
                .dealPage(new AtomicInteger(1))
                .build();
        //队列处理user info
        rabbitTemplate.convertAndSend(RabbitMqKey.NOTICE_COURSE_USER_INFO_UNAME, queueEntity2);

        CourseQueueEntity queueEntity3 = CourseQueueEntity.builder()
                .classId(Long.valueOf("86980"))
                .liveId(Long.valueOf("86981"))
                .startTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 6))
                .endTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 16))
                .dealPage(new AtomicInteger(1))
                .build();
        //队列处理user info
        rabbitTemplate.convertAndSend(RabbitMqKey.NOTICE_COURSE_USER_INFO_UNAME, queueEntity3);



    }
}
