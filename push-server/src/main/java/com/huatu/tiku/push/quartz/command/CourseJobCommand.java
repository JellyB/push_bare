package com.huatu.tiku.push.quartz.command;

import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.dao.biz.CourseInfoMapper;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.JobScannedEnum;
import com.huatu.tiku.push.enums.NoticeParentTypeEnum;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.Command;
import com.huatu.tiku.push.quartz.constant.JobParent;
import com.huatu.tiku.push.quartz.factory.CourseJobFactory;
import com.huatu.tiku.push.quartz.factory.CourseUserJobFactory;
import com.huatu.tiku.push.quartz.listener.QuartzTriggerListener;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-09 上午10:28
 **/
@Service(value = "courseJobCommand")
@Slf4j
public class CourseJobCommand extends Command {


    @Autowired
    private QuartzTriggerListener quartzTriggerListener;

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;


    /**
     * 搜索业务数据
     *
     * @return
     * @throws BizException
     */
    @Override
    protected List<CourseInfo> searchBizData() throws BizException {

        List<Integer> scannedAble = Lists.newArrayList();
        scannedAble.add(JobScannedEnum.NOT_YET_SCANNED.getValue());
        scannedAble.add(JobScannedEnum.REMIND_JOB_HAS_CREATED_OR_OBSOLETE.getValue());
        Example example = new Example(CourseInfo.class);
        example.and()
                .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue())
                .andIn("scanned", scannedAble);
        example.orderBy("createTime").desc();

        List<CourseInfo> courseInfos = courseInfoMapper.selectByExample(example);
        log.info("course info size:{}", courseInfos.size());
        return courseInfos;
    }

    /**
     * 判断是否为新job
     *
     * @param o
     * @return
     * @throws BizException
     */
    @Override
    protected Optional<NoticeTypeEnum> judgeCurrentDataJobAvailableAndJobType(Object o) throws BizException {
        CourseInfo courseInfo = (CourseInfo) o;
        final Timestamp startTime = courseInfo.getStartTime();
        JobScannedEnum jobScannedEnum = JobScannedEnum.create(courseInfo.getScanned());
        NoticeTypeEnum noticeTypeEnum = NoticeTimeParseUtil.parseTime2NoticeType(startTime.getTime(), NoticeParentTypeEnum.COURSE, jobScannedEnum);
        /**
         * 更新为不能构建为定时任务
         */
        if(null == noticeTypeEnum){
            markCurrentCourseNotJob(courseInfo);
            return Optional.ofNullable(null);
        }
        return Optional.of(noticeTypeEnum);
    }

    /**
     * 更新job信息
     *
     * @param o
     * @return
     * @throws BizException
     */
    @Override
    protected int updateEntityScannedStatus(Object o) throws BizException {
        CourseInfo courseInfo = (CourseInfo) o;
        CourseInfo courseInfo_ = new CourseInfo();
        courseInfo_.setId(courseInfo.getId());
        courseInfo_.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        JobScannedEnum jobScannedEnum = JobScannedEnum.create(courseInfo.getScanned());
        if(jobScannedEnum.equals(JobScannedEnum.NOT_YET_SCANNED)){
            courseInfo_.setScanned(JobScannedEnum.REMIND_JOB_HAS_CREATED_OR_OBSOLETE.getValue());
        }else if(jobScannedEnum.equals(JobScannedEnum.REMIND_JOB_HAS_CREATED_OR_OBSOLETE)){
            courseInfo_.setScanned(JobScannedEnum.JOB_WAITING.getValue());
        }else{
            courseInfo_.setScanned(JobScannedEnum.SCANNED_CAN_NOT_CREATE_JOB.getValue());
        }
        return courseInfoMapper.updateByPrimaryKeySelective(courseInfo_);

    }

    /**
     * 标记当前课程为非任务
     * @return
     */
    private int markCurrentCourseNotJob(CourseInfo courseInfo){
        try{
            CourseInfo courseInfo_ = new CourseInfo();
            courseInfo_.setId(courseInfo.getId());
            courseInfo_.setScanned(JobScannedEnum.SCANNED_CAN_NOT_CREATE_JOB.getValue());
            courseInfo_.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return courseInfoMapper.updateByPrimaryKeySelective(courseInfo_);
        }catch (Exception e){
          throw new BizException(NoticePushErrors.UPDATE_COURSE_INFO_FAILED);
        }

    }


    /**
     * 如果需要创建队列跑数据
     *
     * @param o
     * @param noticeTypeEnum
     * @param startTime
     * @throws BizException
     */
    @Override
    protected JobParent needExtraJob(NoticeTypeEnum noticeTypeEnum, Date startTime, final Object o) throws BizException {
        CourseInfo courseInfo = (CourseInfo) o;

        /**
         * 队列处理user info
         * 存储用户数据到 数据库 & redis
         */
        JobParent jobParent = CourseUserJobFactory.create(noticeTypeEnum, startTime, courseInfo);
        return jobParent;
    }

    /**
     * 构建job, 如果需要创建队列跑数据
     *
     * @param o
     * @throws BizException
     */
    @Override
    protected JobParent createJob(NoticeTypeEnum noticeTypeEnum, Object o) throws BizException {
        CourseInfo courseInfo = (CourseInfo) o;
        JobParent jobParent = CourseJobFactory.create(noticeTypeEnum, courseInfo);
        updateEntityScannedStatus(courseInfo);
        return jobParent;
    }

    /**
     * scheduleJob
     *
     * @param jobParent
     * @throws BizException
     */
    @Override
    protected void scheduleJob(JobParent jobParent) throws BizException {
        try{
            Date date = scheduler.scheduleJob(jobParent.getJobDetail(), jobParent.getTrigger());
            scheduler.getListenerManager().addTriggerListener(quartzTriggerListener);
            log.info("job 执行时间:{}", NoticeTimeParseUtil.wholeDateFormat.format(date));
            log.info("add new job:{}", jobParent.getJobDetail().getKey().getName());
        }catch (SchedulerException e){
            log.error("启动任务失败！jobName:{}, triggerName:{},e:{}",
                    jobParent.getJobDetail().getKey().getName(), jobParent.getTrigger().getKey().getName(),e);
        }
    }
}

