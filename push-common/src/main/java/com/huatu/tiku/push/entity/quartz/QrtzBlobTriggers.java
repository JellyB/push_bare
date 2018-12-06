package com.huatu.tiku.push.entity.quartz;


import lombok.Data;

@Data
public class QrtzBlobTriggers {
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
    private byte[] blobData;
}