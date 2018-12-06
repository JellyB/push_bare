package com.huatu.tiku.push.entity.quartz;

import lombok.Data;

@Data
public class QrtzSimpleTriggers{
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
    private Long repeatCount;

    /**
     * 
     */
    private Long repeatInterval;

    /**
     * 
     */
    private Long timesTriggered;
}