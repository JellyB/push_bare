package com.huatu.tiku.push.quartz.job;

import com.huatu.tiku.push.service.command.Receiver;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：总经理job
 *
 * @author biguodong
 * Create time 2018-11-08 下午8:08
 **/

@Slf4j
@Component
public class ManagerJob implements BaseQuartzJob {


    @Autowired
    private Receiver receiver;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    /**
     * execute
     *
     * @param executionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext executionContext) throws JobExecutionException {

        JobKey jobKey = executionContext.getJobDetail().getKey();
        log.info(jobKey + " 在 " + dateFormat.format(new Date())+"催促经理干活了~~~");
        receiver.acceptCommand();
    }
}
