package com.huatu.tiku.push.enums;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-01-10 上午9:52
 **/

public enum  CorrectDealEnum {
    IGNORE("忽略"),
    NORMAL("正常");

    private String value;
    CorrectDealEnum(String value) {
        this.value = value;
    }
}
