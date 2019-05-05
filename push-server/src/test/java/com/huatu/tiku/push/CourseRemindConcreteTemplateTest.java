package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.template.CourseRemindConcreteTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.sql.Timestamp;
import java.util.List;

/**
 * 描述：创建课程提醒推送
 *
 * @author biguodong
 * Create time 2018-11-22 下午6:04
 **/
@Slf4j
public class CourseRemindConcreteTemplateTest extends PushBaseTest{

    @Autowired
    private CourseRemindConcreteTemplate courseRemindConcreteTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void addMembers(){
        long classId = 86980l;
        long startValue = 233982024L;
        List<Long> userIds = Lists.newArrayList();
        String key = NoticePushRedisKey.getCourseClassId(classId);
        SetOperations setOperations = redisTemplate.opsForSet();
        for(int i = 0 ; i < 400; i ++){
            startValue = startValue + i;
            userIds.add(startValue);
        }
        userIds.add(233906356L);
        setOperations.add(key, userIds.toArray());
        log.error("当前课程报名用户量:{}", setOperations.size(key));
    }


    @Test
    public void test(){
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(20181126L);
        courseInfo.setLiveId(20181126L);
        courseInfo.setClassId(86980l);
        courseInfo.setTeacher("多线程测试123");
        courseInfo.setClassImg("http://p.htwx.net/images/course_default.jpg");

        courseInfo.setClassTitle("多线程测试123");
        courseInfo.setSection("多线程测试123");
        courseInfo.setStartTime(new Timestamp(1545116160000L));
        courseInfo.setEndTime(new Timestamp(1643240800000L));
        courseInfo.setIsLive("1");
        courseRemindConcreteTemplate.dealDetailJob(NoticeTypeEnum.COURSE_READY, JSONObject.toJSONString(courseInfo));
    }

    @Test
    public void copyProperties(){
        CourseInfo origin = new CourseInfo();
        origin.setTeacher("私有先生");

        CourseInfo newInfo = new CourseInfo();
        newInfo.setTeacher("新的先生");
        newInfo.setSection("hello section!!");

        BeanUtils.copyProperties(newInfo, origin);
        log.info("origin:{}", JSONObject.toJSONString(origin));
        log.info("new:{}", JSONObject.toJSONString(newInfo));
    }
}
