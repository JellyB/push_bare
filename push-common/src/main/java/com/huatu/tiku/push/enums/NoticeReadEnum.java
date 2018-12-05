package com.huatu.tiku.push.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-11 下午9:43
 **/
@Getter
@AllArgsConstructor
public enum  NoticeReadEnum {
    READ(1),
    UN_READ(0);

    private int value;
}
