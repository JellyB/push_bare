package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huatu.common.SuccessMessage;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.dao.Page;
import com.huatu.tiku.push.dao.QuartzJobDao;
import com.huatu.tiku.push.dto.CourseInfoVo;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.entity.quartz.JobAndTriggers;
import com.huatu.tiku.push.entity.quartz.JobAndTriggersResp;
import com.huatu.tiku.push.entity.quartz.QrtzTriggers;
import com.huatu.tiku.push.enums.GroupNameEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.job.*;
import com.huatu.tiku.push.quartz.listener.QuartzTriggerListener;
import com.huatu.tiku.push.service.api.QuartzJobInfoService;
import com.huatu.tiku.push.util.JobKeyUtil;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午4:45
 **/
@Slf4j
@Service
public class QuartzJobInfoServiceImpl implements QuartzJobInfoService {

    @Autowired
    private QuartzJobDao quartzJobDao;


    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Autowired
    private QuartzTriggerListener quartzTriggerListener;

    /**
     * 条件 job
     *
     * @param jobName
     * @return
     * @throws BizException
     */
    @Override
    public Object addJobByName(String jobName) throws BizException {
        try {
            JobDetail jobDetail = JobBuilder
                    .newJob(PrintJobDemo.class)
                    .withIdentity(jobName, GroupNameEnum.COURSE.getGroup()).build();
            Trigger trigger = TriggerBuilder.newTrigger().startAt(DateBuilder.dateOf(18, 38, 20, 8, 11, 2018))
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(100, 10))
                    .build();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("构建定时任务失败：{}", e.getMessage());
        }
        return SuccessMessage.create("创建成功！！");
    }


    /**
     * 添加课程提醒job
     *
     * @param courseJob
     * @return
     * @throws BizException
     */
    @Override
    public Object addCourseRemindJob(CourseJob courseJob) throws BizException {
        NoticeTypeEnum noticeTypeEnum = NoticeTypeEnum.COURSE_REMIND;
        long jobStartTime = System.currentTimeMillis() + courseJob.getTime() * 1000L;
        CourseInfo courseInfo = courseJob.getCourseInfo();
        try {
            JobDetail jobDetail = JobBuilder
                    .newJob(CourseRemindConcreteJob.class)
                    .withIdentity(JobKeyUtil.jobName(noticeTypeEnum, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.jobGroup(noticeTypeEnum))
                    .usingJobData(BaseQuartzJob.CourseBizData, JSONObject.toJSONString(courseInfo))
                    .usingJobData(BaseQuartzJob.BizDataClass, CourseInfo.class.getName())
                    .build();

            SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                    .withIdentity(JobKeyUtil.triggerName(noticeTypeEnum, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.triggerGroup(noticeTypeEnum))
                    .startAt(new Date(jobStartTime))
                    .build();
            scheduler.getListenerManager().addTriggerListener(quartzTriggerListener);
            scheduler.scheduleJob(jobDetail, simpleTrigger);

        }catch (Exception e){
            log.error("add remind job error ",e);
        }
        return SuccessMessage.create("创建成功！！");
    }


    /**
     * 添加课程ready job
     *
     * @param courseJob
     * @return
     * @throws BizException
     */
    @Override
    public Object addCourseReadyJob(CourseJob courseJob) throws BizException {
        NoticeTypeEnum noticeTypeEnum = NoticeTypeEnum.COURSE_READY;
        long jobStartTime = System.currentTimeMillis() + courseJob.getTime() * 1000L;
        CourseInfo courseInfo = courseJob.getCourseInfo();
        try {
            JobDetail jobDetail = JobBuilder
                    .newJob(CourseReadyConcreteJob.class)
                    .withIdentity(JobKeyUtil.jobName(noticeTypeEnum, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.jobGroup(noticeTypeEnum))
                    .usingJobData(BaseQuartzJob.CourseBizData, JSONObject.toJSONString(courseInfo))
                    .usingJobData(BaseQuartzJob.BizDataClass, CourseInfo.class.getName())
                    .build();

            SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                    .withIdentity(JobKeyUtil.triggerName(noticeTypeEnum, String.valueOf(courseInfo.getLiveId())), JobKeyUtil.triggerGroup(noticeTypeEnum))
                    .startAt(new Date(jobStartTime))
                    .build();
            scheduler.getListenerManager().addTriggerListener(quartzTriggerListener);
            scheduler.scheduleJob(jobDetail, simpleTrigger);

        }catch (Exception e){
            log.error("add ready job error ",e);
        }
        return SuccessMessage.create("创建成功！！");
    }

    /**
     * 使用cron 调度添加job
     *
     * @param jobName
     * @param cron
     * @return
     * @throws BizException
     */
    @Override
    public Object addJobByCron(String jobName, String cron) throws BizException {
        try {
            JobDetail jobDetail = JobBuilder
                    .newJob(ManagerJob.class)
                    .withIdentity(jobName, GroupNameEnum.BOSS.getGroup()).build();
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");

            CronTrigger cronTrigger = TriggerBuilder
                    .newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobName, GroupNameEnum.BOSS.getGroup())
                    .withSchedule(cronScheduleBuilder)
                    .build();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (Exception e) {
            log.error("构建定时任务失败：{}", e.getMessage());
        }

        return null;
    }


    /**
     * job 自定义调度
     *
     * @param jobInfo
     * @return
     * @throws BizException
     */
    @Override
    public void  jobreschedule(JobInfo jobInfo) throws BizException {
        try {

            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis() + jobInfo.getTime() * 1000L;
            TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobName(), jobInfo.getJobGroup());

            SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                    .startAt(new Date(startTime))
                    .build();

            SimpleTrigger trigger = null;

            if(null != scheduler.getTrigger(triggerKey)){
                trigger =  (SimpleTrigger)scheduler.getTrigger(triggerKey);
            }else{
                return;
            }
            trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .startAt(new Date(startTime))
                    .endAt(new Date(endTime))
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(jobInfo.getCount(), jobInfo.getCount())).build();

            scheduler.rescheduleJob(triggerKey, simpleTrigger);
        } catch (SchedulerException e) {
            log.error("更新定时任务失败:{}", e);
        }
    }


    /**
     * 查询job 列表
     *
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    @Override
    public Page<JobAndTriggers> list(int page, int size) throws BizException {
        List<JobAndTriggersResp> resps = Lists.newArrayList();

        String sql = getSql();
        long total = getTotal(sql);
        String limitSql = getLimitSql(sql, page, size);
        List<JobAndTriggers> list = quartzJobDao.searchJobListByPage(limitSql);

        list.forEach((JobAndTriggers item) ->{
            JobAndTriggersResp resp = new JobAndTriggersResp();
            BeanUtils.copyProperties(item, resp);
            String startDate = NoticeTimeParseUtil.localDateFormat.format(new Date(item.getStartTime()));
            String endDate = NoticeTimeParseUtil.localDateFormat.format(new Date(item.getEndTime()));
            String prevFireDate = NoticeTimeParseUtil.localDateFormat.format(item.getPrevFireTime());
            String nextFireDate = NoticeTimeParseUtil.localDateFormat.format(item.getNextFireTime());

            resp.setStartDate(startDate);
            resp.setEndDate(endDate);
            resp.setPrevFireDate(prevFireDate);
            resp.setNextFireDate(nextFireDate);
            try{
                Object object = getObjectFromBlob(item.getJobBigData());
                resp.setJobInfo(object);
                resps.add(resp);
            }catch (ClassNotFoundException sE){
                log.error("class not found!");
            }catch (SQLException SQ){
                log.error("sql exception");
            }catch (IOException IO){
                log.error("io exception");
            }

        });
        Page page_ = new Page(resps, total, page, size);
        return page_;
    }


    /**
     * BLOB 转化为 object
     * @param data
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    protected static Object getObjectFromBlob(Blob data)
            throws ClassNotFoundException, IOException, SQLException {

        Object obj = null;
        CourseInfoVo courseInfoVo = null;
        InputStream binaryInput = data.getBinaryStream();
        if (binaryInput != null) {
            ObjectInputStream in = new ObjectInputStream(binaryInput);
            try {
                obj = in.readObject();
                JobDataMap jobDataMap = (JobDataMap) obj;
                if(jobDataMap.size() > 0 && jobDataMap.containsKey(BaseQuartzJob.CourseBizData)){
                    String courseInfoString = (String)jobDataMap.get(BaseQuartzJob.CourseBizData);
                    CourseInfo courseInfo = JSONObject.parseObject(courseInfoString, CourseInfo.class);
                    courseInfoVo = new CourseInfoVo();
                    BeanUtils.copyProperties(courseInfo, courseInfoVo);
                }
            } finally {
                in.close();
            }
        }
        return courseInfoVo;
    }

    /***
     * 分页sql
     * @param sql
     * @param page
     * @param size
     * @return
     */
    private String getLimitSql(String sql, int page, int size){
        int pageOffSet = 0;
        if(page > 0){
            pageOffSet = (page - 1) * size;
        }
        StringBuilder stringBuilder = new StringBuilder(sql);
        stringBuilder.append(" LIMIT ")
                .append(pageOffSet)
                .append(",")
                .append(size);
        return stringBuilder.toString();
    }

    /**
     * 获取total
     * @param sql
     * @return
     */
    private long getTotal(String sql){

        String countSql = "select count(*) " + sql.substring(sql.indexOf("FROM"));
        return quartzJobDao.getQuartzTotal(countSql);
    }

    private String getSql(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" SELECT")
                .append(" QRTZ_JOB_DETAILS.JOB_NAME,")
                .append(" QRTZ_JOB_DETAILS.JOB_GROUP,")
                .append(" QRTZ_JOB_DETAILS.JOB_CLASS_NAME,")
                .append(" QRTZ_JOB_DETAILS.DESCRIPTION,")
                .append(" QRTZ_JOB_DETAILS.JOB_DATA AS JOB_BIG_DATA,")
                .append(" QRTZ_TRIGGERS.TRIGGER_NAME,")
                .append(" QRTZ_TRIGGERS.TRIGGER_GROUP,")
                .append(" QRTZ_TRIGGERS.TRIGGER_STATE,")
                .append(" QRTZ_TRIGGERS.TRIGGER_TYPE,")
                .append(" QRTZ_TRIGGERS.START_TIME,")
                .append(" QRTZ_TRIGGERS.END_TIME,")
                .append(" QRTZ_TRIGGERS.NEXT_FIRE_TIME,")
                .append(" QRTZ_TRIGGERS.PREV_FIRE_TIME,")
                .append(" QRTZ_TRIGGERS.JOB_DATA AS TRIGGER_BIG_DATA,")
                .append(" QRTZ_SIMPLE_TRIGGERS.REPEAT_COUNT,")
                .append(" QRTZ_SIMPLE_TRIGGERS.REPEAT_INTERVAL,")
                .append(" QRTZ_SIMPLE_TRIGGERS.TIMES_TRIGGERED,")
                .append(" QRTZ_CRON_TRIGGERS.CRON_EXPRESSION,")
                .append(" QRTZ_CRON_TRIGGERS.TIME_ZONE_ID")

                .append(" FROM")
                .append(" QRTZ_JOB_DETAILS")
                .append(" JOIN QRTZ_TRIGGERS")
                .append(" ON QRTZ_JOB_DETAILS.JOB_NAME = QRTZ_TRIGGERS.JOB_NAME")
                .append(" LEFT JOIN QRTZ_SIMPLE_TRIGGERS ON QRTZ_TRIGGERS.TRIGGER_NAME = QRTZ_SIMPLE_TRIGGERS.TRIGGER_NAME")
                .append(" LEFT JOIN QRTZ_CRON_TRIGGERS ON QRTZ_TRIGGERS.TRIGGER_NAME = QRTZ_CRON_TRIGGERS.TRIGGER_NAME");
        return stringBuilder.toString();
    }


    /**
     * 根据名称和组获取job信息
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws BizException
     */
    @Override
    public Object selectByJobNameAndGroup(String jobName, String jobGroup) throws BizException {
        Page<JobAndTriggers> page = list(0, 0);
        List<JobAndTriggers> list_ = page.getData().stream()
                .filter(item -> item.getJobName().equals(jobName))
                .filter(item -> item.getJobGroup().equals(jobGroup))
                .collect(Collectors.toList());
        return list_;
    }

    /**
     * 删除job
     *
     * @return
     * @throws BizException
     */
    @Override
    public void deleteJob(List<JobKey> jobKeys, List<TriggerKey> triggerKeys) throws BizException {
        try{
            for (TriggerKey triggerKey : triggerKeys) {
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
            }
            for (JobKey jobKey : jobKeys) {
                scheduler.deleteJob(jobKey);
            }
        }catch (Exception e){
            throw new BizException(NoticePushErrors.JOB_DELETE_FAILED);
        }
    }


    /**
     * 根据业务数据逻辑删除
     *
     * @param bizId
     * @throws BizException
     */
    @Override
    public void deleteJobByBizData(String bizId) throws BizException {

        List<JobKey> jobKeys = Lists.newArrayList();
        List<TriggerKey> triggerKeys = Lists.newArrayList();
        List<QrtzTriggers> triggers = quartzJobDao.getTrigger(bizId);
        if(CollectionUtils.isNotEmpty(triggers)){
            triggers.forEach(item-> jobKeys.add(new JobKey(item.getJobName(), item.getJobGroup())));
            triggers.forEach(item-> triggerKeys.add(new TriggerKey(item.getTriggerName(), item.getTriggerGroup())));
            deleteJob(jobKeys, triggerKeys);
        }else{
            log.info("job un exist:");
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseJob{
        private int time;
        private int count;
        private int second;
        private CourseInfo courseInfo;
    }

    @Data
    @NoArgsConstructor
    public static class JobInfo{
        private String jobName;
        private String jobGroup;
        private int time;
        private int count;
        private int second;

        @Builder
        public JobInfo(String jobName, String jobGroup, int time, int count, int second) {
            this.jobName = jobName;
            this.jobGroup = jobGroup;
            this.time = time;
            this.count = count;
            this.second = second;
        }
    }
}
