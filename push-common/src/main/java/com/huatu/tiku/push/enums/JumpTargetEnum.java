package com.huatu.tiku.push.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-08-03 2:00 PM
 **/

@AllArgsConstructor
@Getter
public enum JumpTargetEnum {

    NOTICE_CENTER(12, "ht://noticeCenter", "消息中心"),
    CORRECT_LIST(13, "ht://correctList", "人工批改记录列表"),
    CORRECT_REPORT(14, "ht://manualReport", "人工批改报告");

    private int androidValue;
    private String iosValue;
    private String text;
}
