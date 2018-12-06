package com.huatu.tiku.push.entity.quartz;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QrtzSimpropTriggers {
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
    private String strProp1;

    /**
     * 
     */
    private String strProp2;

    /**
     * 
     */
    private String strProp3;

    /**
     * 
     */
    private Integer intProp1;

    /**
     * 
     */
    private Integer intProp2;

    /**
     * 
     */
    private Long longProp1;

    /**
     * 
     */
    private Long longProp2;

    /**
     * 
     */
    private BigDecimal decProp1;

    /**
     * 
     */
    private BigDecimal decProp2;

    /**
     * 
     */
    private String boolProp1;

    /**
     * 
     */
    private String boolProp2;
}