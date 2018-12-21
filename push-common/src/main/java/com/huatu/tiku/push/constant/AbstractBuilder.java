package com.huatu.tiku.push.constant;

import com.huatu.tiku.push.enums.NoticeTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：抽象builder
 *
 * @author biguodong
 * Create time 2018-12-21 上午10:21
 **/
public abstract class AbstractBuilder {

    public NoticeTypeEnum noticeTypeEnum;

    public NoticeTypeEnum getNoticeTypeEnum() {
        return noticeTypeEnum;
    }

    public void setNoticeTypeEnum(NoticeTypeEnum noticeTypeEnum) {
        this.noticeTypeEnum = noticeTypeEnum;
    }

    public Map<String, Object> params = new HashMap<>();

    /**
     * get params
     * @return
     */
    public abstract Map<String, Object> getParams();
}
