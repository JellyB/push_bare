package com.huatu.tiku.push.entity.quartz;

import lombok.Data;

@Data
public class QrtzSchedulerState {
    /**
     * 
     */
    private String schedName;

    /**
     * 
     */
    private String instanceName;

    /**
     * 
     */
    private Long lastCheckinTime;

    /**
     * 
     */
    private Long checkinInterval;
}