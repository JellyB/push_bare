package com.huatu.tiku.push.constant;

import com.huatu.tiku.push.enums.NoticeTypeEnum;
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
public class CorrectReturnInfo implements Serializable {

    //业务id 答题卡id
    private long bizId;
    //试题类型 0 单题 1 套题
    private int questionType;

    private long answerCardId;

    private String correctTitle;

    private Date submitTime;

    private long userId;

    private String returnContent;

    private Date dealDate;

    private NoticeTypeEnum noticeType;

    @Builder
    public CorrectReturnInfo(long bizId, int questionType, long answerCardId, String correctTitle, Date submitTime, long userId, String returnContent, Date dealDate, NoticeTypeEnum noticeType) {
        this.bizId = bizId;
        this.questionType = questionType;
        this.answerCardId = answerCardId;
        this.correctTitle = correctTitle;
        this.submitTime = submitTime;
        this.userId = userId;
        this.returnContent = returnContent;
        this.dealDate = dealDate;
        this.noticeType = noticeType;
    }
}
