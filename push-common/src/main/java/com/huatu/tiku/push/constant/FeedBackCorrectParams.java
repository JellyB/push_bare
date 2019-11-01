package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 上午11:23
 **/
public class FeedBackCorrectParams extends FeedBackParams {

    public static final String DETAIL_TYPE = "correct";

    private static final String QUESTION_ID = "questionId";

    public static final String SOURCE = "source";

    public static final String REPLY = "reply";
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

        public static FeedBackCorrectParams.Builder builder(String type){
            Builder builder = new FeedBackCorrectParams.Builder();
            builder.getParams().put("type", type);
            return builder;
        }

        public FeedBackCorrectParams.Builder bizId(long bizId){
            this.params.put(BIZ_ID, bizId);
            return this;
        }

        public FeedBackCorrectParams.Builder feedBackId(long feedBackId){
            this.params.put(BIZ_ID, feedBackId);
            return this;
        }

        public FeedBackCorrectParams.Builder questionId(long questionId){
            this.params.put(QUESTION_ID, questionId);
            return this;
        }

        public FeedBackCorrectParams.Builder source(String source){
            this.params.put(SOURCE, source);
            return this;
        }

        public FeedBackCorrectParams.Builder reply(String reply){
            this.params.put(REPLY, reply);
            return this;
        }


        public FeedBackCorrectParams.Builder build(){
            return this;
        }
    }
}
