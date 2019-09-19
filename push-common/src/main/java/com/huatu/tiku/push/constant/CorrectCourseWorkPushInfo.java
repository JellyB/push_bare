package com.huatu.tiku.push.constant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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

    //业务id 答题卡id
    private long bizId;

    private long userId;

    private int netClassId;

    private long syllabusId;

    @Builder
    public CorrectCourseWorkPushInfo(long bizId, long userId, int netClassId, long syllabusId) {
        this.bizId = bizId;
        this.userId = userId;
        this.netClassId = netClassId;
        this.syllabusId = syllabusId;
    }
}
