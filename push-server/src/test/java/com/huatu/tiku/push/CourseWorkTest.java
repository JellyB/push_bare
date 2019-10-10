package com.huatu.tiku.push;

import com.huatu.tiku.push.constant.CorrectCourseWorkPushInfo;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReportService;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReturnService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-10-10 8:41 PM
 **/

@Slf4j
public class CourseWorkTest extends PushBaseTest{

    @Autowired
    private CorrectCourseWorkReturnService correctCourseWorkReturnService;

    @Autowired
    private CorrectCourseWorkReportService correctCourseWorkReportService;

    @Test
    public void test() throws Exception{
        CorrectCourseWorkPushInfo correctCourseWorkPushInfo = new CorrectCourseWorkPushInfo();
        for (int i = 0; i < 100; i++) {
            correctCourseWorkPushInfo.setLessonId(10999 + i);
            correctCourseWorkPushInfo.setBizId(20001101 + i);
            correctCourseWorkPushInfo.setNetClassId(70827);
            correctCourseWorkPushInfo.setLessonId(8888 + i);
            correctCourseWorkPushInfo.setType("N");
            correctCourseWorkPushInfo.setSubmitTime(new Date());
            correctCourseWorkPushInfo.setUserId(233982024);
            correctCourseWorkPushInfo.setReturnContent("字迹潦草1232");
            correctCourseWorkPushInfo.setStem("2019 国考万人模考第" + i);
            correctCourseWorkPushInfo.setImg("http://upload.htexam.com/classimg/class/1569838608.jpg");
            correctCourseWorkPushInfo.setIsLive(1);
            correctCourseWorkReturnService.send(correctCourseWorkPushInfo);
            TimeUnit.MILLISECONDS.sleep(new Random(1000).nextLong());
            correctCourseWorkPushInfo.setType("T");
            correctCourseWorkReportService.send(correctCourseWorkPushInfo);
        }
    }
}
