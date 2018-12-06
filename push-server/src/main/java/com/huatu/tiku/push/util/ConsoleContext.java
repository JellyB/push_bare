package com.huatu.tiku.push.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author biguodong
 */
public class ConsoleContext implements Serializable {

    private ConsoleContext(){}

    private static class SingletonHolder {
        private static final ConsoleContext INSTANCE = new ConsoleContext();
    }

    public static final ConsoleContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Map<String,Object> requestHeader = new HashMap();

    public Map<String, Object> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Map<String, Object> requestHeader) {
        this.requestHeader = requestHeader;
    }
}
