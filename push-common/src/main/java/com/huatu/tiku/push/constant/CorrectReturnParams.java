package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午10:34
 **/
public class CorrectReturnParams extends CorrectParams{

    public static final String DETAIL_TYPE = "correctList";

    private static final String TOPIC_TYPE = "topicType";

    /**
     * notice type
     *
     * @return
     */
    @Override
    public String getType() {
        return DETAIL_TYPE;
    }

    public static class Builder{
        private Map<String, Object> params = new HashMap<>();

        public Map<String, Object> getParams() {
            return params;
        }

        public static CorrectReturnParams.Builder builder(){
            CorrectReturnParams.Builder builder = new CorrectReturnParams.Builder();
            return builder;
        }

        public CorrectReturnParams.Builder bizId(long bizId){
            this.params.put(BIZ_ID, bizId);
            return this;
        }

        public CorrectReturnParams.Builder topicType(int questionType){
            this.params.put(TOPIC_TYPE, questionType);
            return this;
        }

        public CorrectReturnParams.Builder build(){
            return this;
        }
    }
}
