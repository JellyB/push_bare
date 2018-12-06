package com.huatu.tiku.push.constant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：课程queue entity amq
 *
 * @author biguodong
 * Create time 2018-11-13 上午10:12
 **/
@Getter
@Setter
@NoArgsConstructor
public class CourseQueueEntity {

    public static final int DEFAULT_PAGE = 1;


    private long liveId;

    private long classId;

    private long userId;

    private Timestamp startTime;

    private Timestamp endTime;

    private AtomicInteger dealPage;

    @Builder

    public CourseQueueEntity(long liveId, long classId, long userId, Timestamp startTime, Timestamp endTime, AtomicInteger dealPage) {
        this.liveId = liveId;
        this.classId = classId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dealPage = dealPage;
    }
}
