package com.huatu.tiku.push.quartz.job;

import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.template.CourseEndConcreteTemplate;
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
public class CourseWorkConcreteJob implements BaseQuartzJob{

    @Autowired
    private CourseEndConcreteTemplate courseEndConcreteTemplate;
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
            log.info("课后作业推送数据:{}", bizData);
            courseEndConcreteTemplate.dealNoticeRemote(bizData);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
