package com.huatu.tiku.push.quartz.job;

import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.template.CourseRemindConcreteTemplate;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午8:07
 **/
@Slf4j
public class CourseRemindConcreteJob implements BaseQuartzJob{

    @Autowired
    private CourseRemindConcreteTemplate courseRemindConcreteTemplate;
    /**
     * execute
     *
     * @param executionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext executionContext) throws JobExecutionException {
        try{
            String bizData = String.valueOf(executionContext.getJobDetail().getJobDataMap().get(CourseBizData));
            log.info("课程提醒推送课程内容:{}", bizData);
            courseRemindConcreteTemplate.dealDetailJob(NoticeTypeEnum.COURSE_REMIND, bizData);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
