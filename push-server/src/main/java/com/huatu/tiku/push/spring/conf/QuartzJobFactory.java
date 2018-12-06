package com.huatu.tiku.push.spring.conf;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午4:16
 **/

@Component
public class QuartzJobFactory extends AdaptableJobFactory{
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    /**
     * Create an instance of the specified job class.
     * <p>Can be overridden to post-process the job instance.
     *
     * @param bundle the TriggerFiredBundle from which the JobDetail
     *               and other info relating to the trigger firing can be obtained
     * @return the job instance
     * @throws Exception if job instantiation failed
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
