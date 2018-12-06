package com.huatu.tiku.push.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：quartz group 分组
 *
 * @author biguodong
 * Create time 2018-11-08 上午11:39
 **/
@Getter
@AllArgsConstructor
public enum GroupNameEnum {
    BOSS("bossGroup"), COURSE("courseGroup"), MOCK("mockGroup"), ORDER("orderGroup"),FEEDBACK("feedBack");

    private String group;
}
