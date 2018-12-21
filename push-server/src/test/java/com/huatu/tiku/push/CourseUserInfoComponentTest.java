package com.huatu.tiku.push;

import com.huatu.tiku.push.constant.CourseQueueEntity;
import com.huatu.tiku.push.service.api.UserInfoComponent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：拉取用户信息job
 *
 * @author biguodong
 * Create time 2018-11-15 下午11:14
 **/
public class CourseUserInfoComponentTest extends PushBaseTest{


    @Autowired
    private UserInfoComponent courseUserInfoComponent;

    @Test
    public void test(){
        long startTime = System.currentTimeMillis() + 1000 * 60 * 20L;
        long endTime = System.currentTimeMillis() + 1000 * 60 * 200L;

        CourseQueueEntity courseQueueEntity = CourseQueueEntity.builder()
                .classId(87092L)
                .liveId(998999L)

                .startTime(new Timestamp(startTime))
                .endTime(new Timestamp(endTime))
                .dealPage(new AtomicInteger(CourseQueueEntity.DEFAULT_PAGE))
                .build();

        courseUserInfoComponent.fetchUserName(courseQueueEntity);

    }
}
