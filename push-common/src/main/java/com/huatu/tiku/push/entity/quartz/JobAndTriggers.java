package com.huatu.tiku.push.entity.quartz;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-09 下午7:13
 **/
@Data
@NoArgsConstructor
public class JobAndTriggers {

    private String jobName;
    private String jobGroup;
    private String jobClassName;
    private String description;
    private Blob jobBigData;
    private String triggerName;
    private String triggerGroup;
    private String triggerState;
    private String triggerType;
    private Long startTime;
    private Long endTime;
    private Long nextFireTime;
    private Long prevFireTime;
    private Blob triggerBigData;
    private Long repeatCount;
    private Long repeatInterval;
    private Long timesTriggered;
    private String cronExpression;
    private String timeZoneId;
    private Object jobData;
    private Object triggerData;







    @Builder

    public JobAndTriggers(String jobName, String jobGroup, String jobClassName, String description, Blob jobBigData, String triggerName, String triggerGroup, String triggerState, String triggerType, Long startTime, Long endTime, Long nextFireTime, Long prevFireTime, Blob triggerBigData, Long repeatCount, Long repeatInterval, Long timesTriggered, String cronExpression, String timeZoneId, Object jobData, Object triggerData) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobClassName = jobClassName;
        this.description = description;
        this.jobBigData = jobBigData;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.triggerState = triggerState;
        this.triggerType = triggerType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nextFireTime = nextFireTime;
        this.prevFireTime = prevFireTime;
        this.triggerBigData = triggerBigData;
        this.repeatCount = repeatCount;
        this.repeatInterval = repeatInterval;
        this.timesTriggered = timesTriggered;
        this.cronExpression = cronExpression;
        this.timeZoneId = timeZoneId;
        this.jobData = jobData;
        this.triggerData = triggerData;
    }
}
