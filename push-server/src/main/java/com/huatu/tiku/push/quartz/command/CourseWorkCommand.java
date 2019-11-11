package com.huatu.tiku.push.quartz.command;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.dao.CourseInfoMapper;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.JobScannedEnum;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.Command;
import com.huatu.tiku.push.quartz.constant.JobParent;
import com.huatu.tiku.push.quartz.factory.CourseJobFactory;
import com.huatu.tiku.push.quartz.listener.QuartzTriggerListener;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-11-07 8:49 PM
 **/
@Slf4j
@Component
public class CourseWorkCommand extends Command {

    @Autowired
    private QuartzTriggerListener quartzTriggerListener;

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    /**
     * Command
     * 搜索业务数据
     *
     * @return
     * @throws BizException
     */
    @Override
    protected List searchBizData() throws BizException {
        Example example = new Example(CourseInfo.class);
        example.and()
                .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue())
                .andEqualTo("scanned", JobScannedEnum.JOB_WAITING)
                .andGreaterThanOrEqualTo("startTime", System.currentTimeMillis());

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
        return Optional.of(NoticeTypeEnum.REMOTE_NOTICE);
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
        return 0;
    }

    /**
     * 需要一个额外的job
     *
     * @param noticeTypeEnum
     * @param startTime
     * @param o
     * @return
     * @throws BizException
     */
    @Override
    protected JobParent needExtraJob(NoticeTypeEnum noticeTypeEnum, Date startTime, Object o) throws BizException {
        return null;
    }

    /**
     * 构建job
     *
     * @param noticeTypeEnum
     * @param o
     * @return
     * @throws BizException
     */
    @Override
    protected JobParent createJob(NoticeTypeEnum noticeTypeEnum, Object o) throws BizException {
        CourseInfo courseInfo = (CourseInfo) o;
        JobParent jobParent = CourseJobFactory.alert(noticeTypeEnum, courseInfo);
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
            log.info("job 执行时间:{}", NoticeTimeParseUtil.wholeDateFormat.print(date.getTime()));
            log.info("add new job:{}", jobParent.getJobDetail().getKey().getName());
        }catch (SchedulerException e){
            log.error("启动任务失败！jobName:{}, triggerName:{},e:{}",
                    jobParent.getJobDetail().getKey().getName(), jobParent.getTrigger().getKey().getName(),e);
        }
    }
}
