package com.huatu.tiku.push.constant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 上午9:40
 **/

@Getter
@Setter
@NoArgsConstructor
public class CorrectCourseWorkPushInfo implements Serializable {

    public static final String RETURN = "N";

    public static final String REPORT = "T";

    //业务id 答题卡id
    private long bizId;

    private long userId;

    private long netClassId;

    private long syllabusId;

    /**
     * N 被退回
     * T 出报告
     */
    private String type;

    private Date submitTime;

    private String stem;

    private String returnContent;


    @Builder
    public CorrectCourseWorkPushInfo(long bizId, long userId, long netClassId, long syllabusId, String type, Date submitTime, String stem, String returnContent) {
        this.bizId = bizId;
        this.userId = userId;
        this.netClassId = netClassId;
        this.syllabusId = syllabusId;
        this.type = type;
        this.submitTime = submitTime;
        this.stem = stem;
        this.returnContent = returnContent;
    }
}
