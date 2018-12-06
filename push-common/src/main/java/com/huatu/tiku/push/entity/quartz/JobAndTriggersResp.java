package com.huatu.tiku.push.entity.quartz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-18 下午10:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAndTriggersResp<T> {
    private String jobName;
    private String jobGroup;
    private String jobClassName;
    private String description;
    private T      jobInfo;
    private String triggerName;
    private String triggerGroup;
    private String triggerState;
    private String triggerType;
    private String startDate;
    private String endDate;
    private String nextFireDate;
    private String prevFireDate;
    private T    triggerInfo;
    private Long repeatCount;
    private Long repeatInterval;
    private Long timesTriggered;
    private String cronExpression;
    private String timeZoneId;
}
