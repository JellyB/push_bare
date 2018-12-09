package com.huatu.tiku.push.service.api.impl;

import com.google.common.collect.Maps;
import com.huatu.tiku.push.response.DataMigrateResp;
import com.huatu.tiku.push.service.feign.DataMigrateFeign;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 下午5:23
 **/
@Service
@Slf4j
public class DataMigrateService {

    private static final AtomicInteger pageNo = new AtomicInteger(1);

    @Autowired
    private DataMigrateFeign dataMigrateFeign;




    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;


    public void execute(int size){
        try{
            Map<String, Object> params = Maps.newHashMap();
            params.put("page", pageNo.get());
            params.put("size", size);

            DataMigrateResp dataMigrateResp = dataMigrateFeign.migrate(params);
            DataMigrateResp.InnerData innerData = dataMigrateResp.getData();
            if(innerData.getNext() > 0 && innerData.getList().size() > 0){
                pageNo.incrementAndGet();
            }else{
                TriggerKey triggerKey = new TriggerKey("pandoraMigrate2Push", "Migrate");
                JobKey jobKey = new JobKey("pandoraMigrate2Push", "Migrate");
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                scheduler.deleteJob(jobKey);
            }
        }catch (Exception e){
            log.error("******************************");
            log.error(">>>> 远程调用pandora 异常 <<<<<<");
            log.error("******************************");
        }
    }
}
