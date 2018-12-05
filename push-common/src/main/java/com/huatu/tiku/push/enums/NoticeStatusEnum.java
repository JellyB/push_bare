package com.huatu.tiku.push.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-11 下午10:21
 **/
@Getter
@AllArgsConstructor
public enum  NoticeStatusEnum {

    NORMAL(1),
    DELETE_LOGIC(0);

    private int value;
}
