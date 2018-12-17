package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 上午11:23
 **/
public class FeedBackSuggestParams extends Params {

    public static final String TYPE = "feedback";

    public static final String DETAIL_TYPE = "suggest";

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
            this.params.put("title", title);
            return this;
        }

        public FeedBackSuggestParams.Builder reply(String reply){
            this.params.put("reply", reply);
            return this;
        }


        public FeedBackSuggestParams.Builder build(){
            return this;
        }
    }
}
