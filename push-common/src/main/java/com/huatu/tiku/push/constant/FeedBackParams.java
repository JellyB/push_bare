package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 上午11:23
 **/
public class FeedBackParams extends Params {

    public static final String TYPE = "feedback";

    public static final String CORRECT = "correct";

    public static final String SUGGEST = "suggest";

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
        private Map<String, Object> params = new HashMap<>();

        public Map<String, Object> getParams() {
            return params;
        }

        public static FeedBackParams.Builder builder(String type){
            Builder builder = new FeedBackParams.Builder();
            builder.getParams().put("type", type);
            return builder;
        }

        public FeedBackParams.Builder feedBackId(long feedBackId){
            this.params.put(BIZ_ID, feedBackId);
            return this;
        }

        public FeedBackParams.Builder questionId(long questionId){
            this.params.put("questionId", questionId);
            return this;
        }

        public FeedBackParams.Builder source(String source){
            this.params.put("source", source);
            return this;
        }

        public FeedBackParams.Builder reply(String reply){
            this.params.put("reply", reply);
            return this;
        }


        public FeedBackParams.Builder build(){
            return this;
        }
    }
}
