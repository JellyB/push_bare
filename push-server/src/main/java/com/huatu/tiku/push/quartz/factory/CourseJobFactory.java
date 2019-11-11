package com.huatu.tiku.push.quartz.factory;

import com.alibaba.fastjson.JSONObject;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.JobScannedEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.constant.JobParent;
import com.huatu.tiku.push.quartz.job.BaseQuartzJob;
import com.huatu.tiku.push.quartz.job.CourseReadyConcreteJob;
import com.huatu.tiku.push.quartz.job.CourseRemindConcreteJob;
import com.huatu.tiku.push.quartz.job.CourseWorkConcreteJob;
import com.huatu.tiku.push.util.JobKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午9:40
 **/
@Slf4j
public class CourseJobFactory extends AbstractFactory{

    /**
     * 半小时job执行
     */
    private static final long REMIND_START_TIME = 30 * 60 * 1000L;

    /**
     * 2分钟前提醒
     */
    private static final long READY_START_TIME = 2 * 60 * 1000L;


    /**
     * 创建job 工厂方法
     * @param noticeTypeEnum
     * @param courseInfo
     */
    public static JobParent create(NoticeTypeEnum noticeTypeEnum, final CourseInfo courseInfo) throws BizException{
        Class clazz = null;
        if(JobScannedEnum.create(courseInfo.getScanned()).equals(JobScannedEnum.NOT_YET_SCANNED)){
            if(noticeTypeEnum.equals(NoticeTypeEnum.COURSE_READY)){
                clazz = CourseReadyConcreteJob.class;
                noticeTypeEnum = NoticeTypeEnum.COURSE_READY;
            }else if(noticeTypeEnum.equals(NoticeTypeEnum.COURSE_REMIND)){
                clazz = CourseRemindConcreteJob.class;
            }

        }
        if(JobScannedEnum.create(courseInfo.getScanned()).equals(JobScannedEnum.REMIND_JOB_HAS_CREATED_OR_OBSOLETE)){
            clazz = CourseReadyConcreteJob.class;
            noticeTypeEnum = NoticeTypeEnum.COURSE_READY;
        }
        if(null == clazz){
            throw new BizException(NoticePushErrors.JOB_CREATE_BY_FACTORY_FAILED);
        }

        final long jobStartTime = getTimeInAdvance(noticeTypeEnum, courseInfo);

        JobDetail jobDetail = JobBuilder
                .newJob(clazz)
                .withIdentity(JobKeyUtil.jobName(noticeTypeEnum, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.jobGroup(noticeTypeEnum))
                .usingJobData(BaseQuartzJob.CourseBizData, JSONObject.toJSONString(courseInfo))
                .usingJobData(BaseQuartzJob.BizDataClass, CourseInfo.class.getName())
                .build();
        /**
         * 只执行一次
         */
        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(JobKeyUtil.triggerName(noticeTypeEnum, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.triggerGroup(noticeTypeEnum))
                .startAt(new Date(jobStartTime))
                .usingJobData(CourseRemindConcreteJob.CourseBizData, JSONObject.toJSONString(courseInfo))
                .build();

        return JobParent.builder().jobDetail(jobDetail).trigger(simpleTrigger).build();
    }


    /**
     * 创建job 工厂方法
     * @param noticeTypeEnum
     * @param courseInfo
     */
    public static JobParent alert(NoticeTypeEnum noticeTypeEnum, final CourseInfo courseInfo) throws BizException{
        final long jobStartTime = getTimeInAdvance(noticeTypeEnum, courseInfo);

        JobDetail jobDetail = JobBuilder
                .newJob(CourseWorkConcreteJob.class)
                .withIdentity(JobKeyUtil.jobName(noticeTypeEnum, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.jobGroup(noticeTypeEnum))
                .usingJobData(BaseQuartzJob.CourseBizData, JSONObject.toJSONString(courseInfo))
                .usingJobData(BaseQuartzJob.BizDataClass, CourseInfo.class.getName())
                .build();
        /**
         * 只执行一次
         */
        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(JobKeyUtil.triggerName(noticeTypeEnum, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.triggerGroup(noticeTypeEnum))
                .startAt(new Date(jobStartTime))
                .usingJobData(CourseRemindConcreteJob.CourseBizData, JSONObject.toJSONString(courseInfo))
                .build();

        return JobParent.builder().jobDetail(jobDetail).trigger(simpleTrigger).build();
    }



    /**
     * 根据类型获取job 执行时间
     * @param noticeTypeEnum
     * @return
     */
    private static long getTimeInAdvance(NoticeTypeEnum noticeTypeEnum, CourseInfo courseInfo){
        long startTime;
        switch (noticeTypeEnum){
            case COURSE_REMIND:
                startTime = courseInfo.getStartTime().getTime() - REMIND_START_TIME;
                break;
            case COURSE_READY:
                startTime = courseInfo.getStartTime().getTime() - READY_START_TIME;
                break;
            case REMOTE_NOTICE:
                startTime = courseInfo.getEndTime().getTime();
                break;
            default:
                log.error("获取job执行时间失败！");
                throw new BizException(NoticePushErrors.START_TIME_ILLEGAL);
        }

        if(startTime < System.currentTimeMillis()){
            log.error("任务执行时间必须在当前时间之后: 任务类型 :{}, 任务id:{}", noticeTypeEnum.getDetailType(), courseInfo.getLiveId());
            throw new BizException(NoticePushErrors.START_TIME_EXPIRED);
        }
        return startTime;
    }






}
