package com.huatu.tiku.push.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 描述：意见反馈
 *
 * @author biguodong
 * Create time 2018-12-14 上午11:58
 **/
@Getter
@Setter
@NoArgsConstructor
public class SuggestFeedbackInfo implements Serializable {

    /**
     * 反馈id
     */
    private long bizId;

    /**
     * 用户id
     */
    private long userId;

    /**
     * 建议title
     */
    private String suggestTitle;

    /**
     * 建议内容
     */
    private String suggestContent;

    /**
     * 回复title
     */
    private String replyTitle;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 创建时间
     */
    private long createTime;

}
