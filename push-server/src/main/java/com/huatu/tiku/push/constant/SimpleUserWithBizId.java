package com.huatu.tiku.push.constant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 下午11:39
 **/
@Getter
@Setter
@NoArgsConstructor
public class SimpleUserWithBizId implements Serializable {

    private long classId;

    private long liveId;

    private long endTime;

    private List<SimpleUserInfo> data;


    @Builder
    public SimpleUserWithBizId(long classId, long liveId, long endTime, List<SimpleUserInfo> data) {
        this.classId = classId;
        this.liveId = liveId;
        this.endTime = endTime;
        this.data = data;
    }
}
