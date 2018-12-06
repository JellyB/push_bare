package com.huatu.tiku.push.entity.quartz;

import lombok.Data;

@Data
public class QrtzFiredTriggers{
    /**
     * 
     */
    private String schedName;

    /**
     * 
     */
    private String entryId;

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
    private String instanceName;

    /**
     * 
     */
    private Long firedTime;

    /**
     * 
     */
    private Long schedTime;

    /**
     * 
     */
    private Integer priority;

    /**
     * 
     */
    private String state;

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
    private String isNonconcurrent;

    /**
     * 
     */
    private String requestsRecovery;
}