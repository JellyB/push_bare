package com.huatu.tiku.push.entity.quartz;

import lombok.Data;

@Data
public class QrtzCalendars {
    /**
     * 
     */
    private String schedName;

    /**
     * 
     */
    private String calendarName;

    /**
     * 
     */
    private byte[] calendar;
}