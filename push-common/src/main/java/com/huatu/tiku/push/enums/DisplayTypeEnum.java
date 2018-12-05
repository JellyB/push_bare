package com.huatu.tiku.push.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 上午11:30
 **/
@AllArgsConstructor
@Getter
public enum  DisplayTypeEnum {

    MESSAGE(0, "message"),
    NOTIFICATION(1, "notification");

    private int type;

    private String  value;
}
