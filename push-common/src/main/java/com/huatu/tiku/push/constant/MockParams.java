package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 上午11:09
 **/
public class MockParams extends Params {

    public static final String TYPE = "mock";

    public static final String ON_LINE = "online";

    public static final String REMIND = "remind";

    public static final String REPORT = "report";


    /**
     * notice type
     *
     * @return
     */
    @Override
    public String getType() {
        return TYPE;
    }

    public static class Builder{
        private Map<String, String> params = new HashMap<>();

        public Map<String, String> getParams() {
            return params;
        }

        public static MockParams.Builder builder(){
            return new MockParams.Builder();
        }

        public MockParams.Builder mockId(String mockId){
            this.params.put(BIZ_ID, mockId);
            return this;
        }

        public MockParams.Builder mockTime(String mockTime){
            this.params.put("mockTime", mockTime);
            return this;
        }

        public MockParams.Builder build(){
            return this;
        }
    }
}
