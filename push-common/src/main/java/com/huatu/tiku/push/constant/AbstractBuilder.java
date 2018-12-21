package com.huatu.tiku.push.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：抽象builder
 *
 * @author biguodong
 * Create time 2018-12-21 上午10:21
 **/
public abstract class AbstractBuilder {


    public Map<String, String> params = new HashMap<>();

    /**
     * get params
     * @return
     */
    public abstract Map<String, String> getParams();
}
