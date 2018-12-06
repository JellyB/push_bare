package com.huatu.tiku.push.quartz.job;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午4:29
 **/
@Slf4j
@Service
public class PrintJobDemo implements BaseQuartzJob {



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
        log.error("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        log.error("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        log.error("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
    }

    //@PostConstruct
    public void init(){
        try{
            BizData bizData = BizData.builder().name("2341234").sex("2341234").build();
            long time = System.currentTimeMillis();
            time += 5 * 60 * 1000L;
            JobDetail jobDetail = JobBuilder
                    .newJob(PrintJobDemo.class)
                    .withIdentity("PRINT TEST", "PRINT TEST G")
                    .usingJobData(CourseRemindConcreteJob.CourseBizData, JSONObject.toJSONString(bizData))
                    .build();
            /**
             * 只执行一次
             */
            SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                    .withIdentity("PRINT TEST T", "PRINT TEST G T")
                    .startAt(new Date(time))
                    .build();

            scheduler.scheduleJob(jobDetail, simpleTrigger);
        }catch (Exception e){
            log.error("error!!!!", e);
        }
    }

    @Data
    @NoArgsConstructor
    public static class BizData{
        private String name;

        private String sex;

        @Builder
        public BizData(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }
    }
}
