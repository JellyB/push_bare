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
public class CorrectFeedbackInfo implements Serializable {

    private long bizId;

    private long questionId;

    private long userId;

    private String source;

    private String reply;

    private Date dealDate;

    private Integer gold;

    @Builder
    public CorrectFeedbackInfo(long bizId, long questionId, long userId, String source, String reply, Date dealDate, Integer gold) {
        this.bizId = bizId;
        this.questionId = questionId;
        this.userId = userId;
        this.source = source;
        this.reply = reply;
        this.dealDate = dealDate;
        this.gold = gold;
    }
}
