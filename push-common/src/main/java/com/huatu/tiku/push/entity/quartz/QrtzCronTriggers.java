package com.huatu.tiku.push.entity.quartz;

import lombok.Data;

@Data
public class QrtzCronTriggers {
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
    private String cronExpression;

    /**
     * 
     */
    private String timeZoneId;
}