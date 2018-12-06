package com.huatu.tiku.push.quartz.constant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-12 上午11:25
 **/

@Getter
@Setter
@NoArgsConstructor
public class JobParent {

    private JobDetail jobDetail;

    private Trigger trigger;

    @Builder
    public JobParent(JobDetail jobDetail, Trigger trigger) {
        this.jobDetail = jobDetail;
        this.trigger = trigger;
    }
}
