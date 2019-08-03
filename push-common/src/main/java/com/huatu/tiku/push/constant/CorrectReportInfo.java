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
public class CorrectReportInfo implements Serializable {

    //业务id 答题卡id
    private long bizId;
    //试题类型 0 套题 1 单题 2 议论文
    private int topicType;
    // 标准答案 套题 议论文
    private String questionName;

    private long answerCardId;

    private Date submitTime;

    private long userId;

    private Date dealDate;

    private String paperName;

    private String areaName;

    private long paperId;

    private long questionBaseId;

    @Builder
    public CorrectReportInfo(long bizId, int topicType, String questionName, long answerCardId, Date submitTime, long userId, Date dealDate, String paperName, String areaName, long paperId, long questionBaseId) {
        this.bizId = bizId;
        this.topicType = topicType;
        this.questionName = questionName;
        this.answerCardId = answerCardId;
        this.submitTime = submitTime;
        this.userId = userId;
        this.dealDate = dealDate;
        this.paperName = paperName;
        this.areaName = areaName;
        this.paperId = paperId;
        this.questionBaseId = questionBaseId;
    }
}
