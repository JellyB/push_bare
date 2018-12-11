package com.huatu.tiku.push.quartz.job;

import com.huatu.tiku.push.enums.GroupNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;


/**
 * 描述：老板 job
 *
 * @author biguodong
 * Create time 2018-11-08 下午8:40
 **/

@Slf4j
@Component
public class BossJob {

    public static final String BossJob = "bossJob_push";

    private static final String BossJobInterval = "*/59 * * * * ?";

    private static final String BossJobIntervalMin_5 = "0 */5 * * * ?";
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @PostConstruct
    private void init(){

        try {

            if(scheduler.checkExists(JobKey.jobKey(BossJob, GroupNameEnum.BOSS.getGroup()))){
                log.info("job exist:{}", BossJob);
            }else{
                Date date = new Date();
                JobDetail jobDetail = JobBuilder
                        .newJob(ManagerJob.class)
                        .withIdentity(BossJob, GroupNameEnum.BOSS.getGroup()).build();
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(BossJobIntervalMin_5);



                CronTrigger cronTrigger = TriggerBuilder
                        .newTrigger()
                        .forJob(jobDetail)
                        .withIdentity(BossJob, GroupNameEnum.BOSS.getGroup())
                        .withSchedule(cronScheduleBuilder)
                        .build();


                SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                        .withIdentity(BossJob, GroupNameEnum.BOSS.getGroup())
                        .startAt(new Date(1000 * 5 + date.getTime()))
                        .endAt(new Date(1000 * 60 * 10 + date.getTime()))
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(3, 600))
                        .build();

                scheduler.start();
                scheduler.scheduleJob(jobDetail, cronTrigger);
                log.info("isConcurrentExceptionDisallowed:{}", jobDetail.isConcurrentExectionDisallowed());
            }
        } catch (ObjectAlreadyExistsException e){
            try{
                scheduler.resumeJob(JobKey.jobKey(BossJob, GroupNameEnum.BOSS.getGroup()));
            }catch (SchedulerException sc){
                log.info("sc" , e.getMessage());
            }

        } catch (Exception e) {
            log.error("构建老板job失败：{}", e.getMessage());
        }
    }
}
