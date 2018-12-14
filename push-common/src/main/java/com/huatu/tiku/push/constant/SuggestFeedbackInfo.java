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

    private long bizId;

    private long uid;

    private String content;

    private String reply;
}
