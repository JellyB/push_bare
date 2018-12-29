package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 上午11:23
 **/
public class FeedBackSuggestParams extends FeedBackParams {

    public static final String DETAIL_TYPE = "suggest";

    public static final String TITLE = "title";

    public static final String REPLY = "reply";

    public static final String CREATE_TIME = "createTime";

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

        public static FeedBackSuggestParams.Builder builder(String type){
            Builder builder = new FeedBackSuggestParams.Builder();
            builder.getParams().put("type", type);
            return builder;
        }

        public FeedBackSuggestParams.Builder suggestId(long suggestId){
            this.params.put(BIZ_ID, suggestId);
            return this;
        }

        public FeedBackSuggestParams.Builder title(String title){
            this.params.put(TITLE, title);
            return this;
        }

        public FeedBackSuggestParams.Builder reply(String reply){
            this.params.put(REPLY, reply);
            return this;
        }

        public FeedBackSuggestParams.Builder createTime(String createTime){
            this.params.put(CREATE_TIME, createTime);
            return this;
        }


        public FeedBackSuggestParams.Builder build(){
            return this;
        }
    }
}
