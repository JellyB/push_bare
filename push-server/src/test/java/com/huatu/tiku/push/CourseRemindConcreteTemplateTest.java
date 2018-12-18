package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.template.CourseRemindConcreteTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

/**
 * 描述：创建课程提醒推送
 *
 * @author biguodong
 * Create time 2018-11-22 下午6:04
 **/
public class CourseRemindConcreteTemplateTest extends PushBaseTest{

    @Autowired
    private CourseRemindConcreteTemplate courseRemindConcreteTemplate;


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
}
