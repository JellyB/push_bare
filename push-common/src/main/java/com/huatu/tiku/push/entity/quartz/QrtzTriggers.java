package com.huatu.tiku.push.entity.quartz;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QrtzTriggers {
    /**
     * 
     */
    private String schedName;

    /**
     * 
     */
    private String triggerName;

    /**
     * 
     */
    private String triggerGroup;

    /**
     * 
     */
    private String jobName;

    /**
     * 
     */
    private String jobGroup;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Long nextFireTime;

    /**
     * 
     */
    private Long prevFireTime;

    /**
     * 
     */
    private Integer priority;

    /**
     * 
     */
    private String triggerState;

    /**
     * 
     */
    private String triggerType;

    /**
     * 
     */
    private Long startTime;

    /**
     * 
     */
    private Long endTime;

    /**
     * 
     */
    private String calendarName;

    /**
     * 
     */
    private Short misfireInstr;

    /**
     * 
     */
    private byte[] jobData;

    @Builder
    public QrtzTriggers(String schedName, String triggerName, String triggerGroup, String jobName, String jobGroup, String description, Long nextFireTime, Long prevFireTime, Integer priority, String triggerState, String triggerType, Long startTime, Long endTime, String calendarName, Short misfireInstr, byte[] jobData) {
        this.schedName = schedName;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.description = description;
        this.nextFireTime = nextFireTime;
        this.prevFireTime = prevFireTime;
        this.priority = priority;
        this.triggerState = triggerState;
        this.triggerType = triggerType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.calendarName = calendarName;
        this.misfireInstr = misfireInstr;
        this.jobData = jobData;
    }
}