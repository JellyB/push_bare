package com.huatu.tiku.push.constant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-06 下午4:35
 **/

@Getter
@Setter
@NoArgsConstructor

@Builder
public class BaseMsg implements Serializable{

    private String title;

    private String text;

    private Map<String, Object> custom;

    @Builder
    public BaseMsg(String title, String text, Map<String, Object> custom) {
        this.title = title;
        this.text = text;
        this.custom = custom;
    }
}
