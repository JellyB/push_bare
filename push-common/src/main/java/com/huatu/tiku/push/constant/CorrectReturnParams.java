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

    public static final String DETAIL_TYPE = "return";

    private static final String QUESTION_TYPE = "questionType";

    private static final String USER_ID = "userId";

    private static final String RETURN_CONTENT = "returnContent";

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

        public static CorrectReturnParams.Builder builder(String type){
            CorrectReturnParams.Builder builder = new CorrectReturnParams.Builder();
            builder.getParams().put("type", type);
            return builder;
        }

        public CorrectReturnParams.Builder answerCardId(long answerCardId){
            this.params.put(BIZ_ID, answerCardId);
            return this;
        }

        public CorrectReturnParams.Builder questionType(int questionType){
            this.params.put(QUESTION_TYPE, questionType);
            return this;
        }

        public CorrectReturnParams.Builder userId(long userId){
            this.params.put(USER_ID, userId);
            return this;
        }

        public CorrectReturnParams.Builder returnContent(String content){
            this.params.put(RETURN_CONTENT, content);
            return this;
        }

        public CorrectReturnParams.Builder build(){
            return this;
        }
    }
}
