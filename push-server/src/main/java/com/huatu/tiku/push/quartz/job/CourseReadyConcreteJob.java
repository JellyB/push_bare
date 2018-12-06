package com.huatu.tiku.push.quartz.job;

import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.template.CourseReadyConcreteTemplate;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-15 上午1:54
 **/
@Slf4j
public class CourseReadyConcreteJob implements BaseQuartzJob {


    @Autowired
    private CourseReadyConcreteTemplate courseReadyConcreteTemplate;
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
            courseReadyConcreteTemplate.dealDetailJob(NoticeTypeEnum.COURSE_READY, bizData);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
