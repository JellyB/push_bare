package com.huatu.tiku.push.entity.quartz;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QrtzJobDetails {
    /**
     * 
     */
    private String schedName;

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
    private String jobClassName;

    /**
     * 
     */
    private String isDurable;

    /**
     * 
     */
    private String isNonconcurrent;

    /**
     * 
     */
    private String isUpdateData;

    /**
     * 
     */
    private String requestsRecovery;

    /**
     * 
     */
    private byte[] jobData;

    @Builder

    public QrtzJobDetails(String schedName, String jobName, String jobGroup, String description, String jobClassName, String isDurable, String isNonconcurrent, String isUpdateData, String requestsRecovery, byte[] jobData) {
        this.schedName = schedName;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.description = description;
        this.jobClassName = jobClassName;
        this.isDurable = isDurable;
        this.isNonconcurrent = isNonconcurrent;
        this.isUpdateData = isUpdateData;
        this.requestsRecovery = requestsRecovery;
        this.jobData = jobData;
    }
}