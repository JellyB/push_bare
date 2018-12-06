package com.huatu.tiku.push.constant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 上午11:32
 **/
@Getter
@Setter
@NoArgsConstructor
public class SimpleUserInfo implements Serializable{

    private String userName;

    private long userId;

    @Builder
    public SimpleUserInfo(String userName, long userId) {
        this.userName = userName;
        this.userId = userId;
    }
}
