package com.huatu.tiku.push.cast;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 下午10:31
 **/
@Data
@NoArgsConstructor
public class PushResult implements Serializable{

    private String ret;

    private JSONObject data;

    @Builder
    public PushResult(String ret, JSONObject data) {
        this.ret = ret;
        this.data = data;
    }
}
