package com.huatu.tiku.push.quartz.factory;

import com.alibaba.fastjson.JSON;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.constant.JobParent;
import com.huatu.tiku.push.quartz.job.BaseQuartzJob;
import com.huatu.tiku.push.quartz.job.CourseUserInfoConcreteJob;
import com.huatu.tiku.push.util.JobKeyUtil;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.Date;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-15 下午10:29
 **/

@Slf4j
public class CourseUserJobFactory {


    private static final String USER = "user";

    /**
     * 创建获取用户信息job,任务开始前10min执行每隔3min执行一次
     * @param noticeTypeEnum
     * @param courseInfo
     * @param jobStartTime
     * @return
     * @throws BizException
     */
    public static JobParent create(NoticeTypeEnum noticeTypeEnum, Date jobStartTime, final CourseInfo courseInfo) throws BizException {

        final long fetchUserInfoStartTime = jobStartTime.getTime() - NoticeTimeParseUtil.COURSE_USER_FETCH_INTERVAL;
        JobDetail jobDetail = JobBuilder
                .newJob(CourseUserInfoConcreteJob.class)
                .withIdentity(JobKeyUtil.jobName(noticeTypeEnum, USER, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.jobGroup(noticeTypeEnum, USER))
                .usingJobData(BaseQuartzJob.CourseBizData, JSON.toJSONString(courseInfo))
                .usingJobData(BaseQuartzJob.BizDataClass, CourseInfo.class.getName())
                .build();

        SimpleTrigger simpleTrigger =  TriggerBuilder.newTrigger()
                .withIdentity(JobKeyUtil.triggerName(noticeTypeEnum, USER, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.triggerGroup(noticeTypeEnum, USER))
                .startAt(new Date(fetchUserInfoStartTime))
                .endAt(jobStartTime)
                .usingJobData(BaseQuartzJob.CourseBizData, JSON.toJSONString(courseInfo))
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(3))
                .build();

        return JobParent.builder().jobDetail(jobDetail).trigger(simpleTrigger).build();
    }
}
