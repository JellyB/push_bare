package com.huatu.tiku.push.quartz.job;

import com.huatu.tiku.push.service.api.impl.DataMigrateService;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cms.PasswordRecipientId;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 下午3:56
 **/

@Slf4j
@Component
public class PandoraDataMoveJob implements BaseQuartzJob {

    private static int DEFAULT_SIZE = 500;

    @Autowired
    private DataMigrateService dataMigrateService;


    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;


    /**
     * execute
     *
     * @param executionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext executionContext) throws JobExecutionException {
        dataMigrateService.execute(DEFAULT_SIZE);
    }


    @PostConstruct
    public void init(){
        try{

            JobDetail jobDetail = JobBuilder
                    .newJob(PandoraDataMoveJob.class)
                    .withIdentity("pandoraMigrate2Push", "Migrate")
                    .build();

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("pandoraMigrate2Push", "Migrate")
                    .forJob(jobDetail)
                    .startAt(new Date())
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(30))
                    .build();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
            log.info(">>>>>>>>>  a job for migrate data from pandora 2 push server init");
        }catch (Exception e){
            log.error("err!", e);
        }
    }
}
