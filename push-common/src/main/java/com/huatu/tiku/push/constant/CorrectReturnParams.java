package com.huatu.tiku.push.constant;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午10:34
 **/
public class CorrectReturnParams extends CorrectParams{

    public static final String DETAIL_TYPE = "correctDetailList";

    private static final String QUESTION_TYPE = "questionType";

    private static final String ANSWER_CARD_ID = "answerCardId";

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

        public CorrectReturnParams.Builder bizId(long answerCardId, int questionType){
            this.params.put(BIZ_ID, Long.parseLong(answerCardId + "" + questionType));
            return this;
        }

        public CorrectReturnParams.Builder answerCardId(long answerCardId){
            this.params.put(ANSWER_CARD_ID, answerCardId);
            return this;
        }

        public CorrectReturnParams.Builder submitTime(Date date){
            this.params.put(SUBMIT_TIME, date);
            return this;
        }

        public CorrectReturnParams.Builder questionType(int questionType){
            this.params.put(QUESTION_TYPE, questionType);
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
