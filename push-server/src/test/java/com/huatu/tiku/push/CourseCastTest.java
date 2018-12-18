package com.huatu.tiku.push;

import com.google.common.collect.Lists;
import com.huatu.tiku.push.request.CourseInfoReq;
import com.huatu.tiku.push.service.api.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 描述：批量新增课程信息
 *
 * @author biguodong
 * Create time 2018-11-17 下午12:43
 **/
@Slf4j
public class CourseCastTest extends PushBaseTest{

    @Autowired
    private CourseService courseService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINESE);

    @Test
    public void saveBatch() throws Exception{
        List<CourseInfoReq.Model> list = Lists.newArrayList();
        final long jobStartTime = dateFormat.parse("2018-11-22 15:00:00").getTime();
        final long jobStopTime = dateFormat.parse("2018-12-28 13:00:00").getTime();
        int intervalMinutes = 3;
        long id = 200000l;
        String classId = "86980";
        String teacher = "毕老师";
        String classImg = "http://p.htwx.net/images/course_default.jpg";
        String classTitle = "%s" + teacher + "国考第%s讲";
        String section = "今晚直播课不见不散";
        for(int i = 0; i < 10; i ++){
            long time = jobStartTime + intervalMinutes * i * 60 *1000L;
            Date data = new Date(time);
            CourseInfoReq.Model model = new CourseInfoReq.Model();
            model.setId(String.valueOf(id + i));
            model.setClassId(classId);
            model.setTeacher(teacher);
            model.setClassImg(classImg);

            model.setClassTitle(String.format(classTitle, simpleDateFormat.format(data), i));
            model.setSection(section);
            model.setStartTime(String.valueOf(data.getTime()));
            model.setEndTime(String.valueOf(jobStopTime));
            model.setIsLive("1");
            list.add(model);
        }
        CourseInfoReq.Batch batch = new CourseInfoReq.Batch();
        batch.setList(list);
        courseService.saveCourseInfoBatch(batch);

    }
}
