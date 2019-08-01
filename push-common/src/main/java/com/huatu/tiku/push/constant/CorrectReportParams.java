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
public class CorrectReportParams extends CorrectParams{

    public static final String DETAIL_TYPE = "report";

    private static final String QUESTION_TYPE = "questionType";

    private static final String ANSWER_CARD_ID = "answerCardId";

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

        public static CorrectReportParams.Builder builder(String type){
            CorrectReportParams.Builder builder = new CorrectReportParams.Builder();
            builder.getParams().put("type", type);
            return builder;
        }

        public CorrectReportParams.Builder bizId(long answerCardId, int questionType){
            this.params.put(BIZ_ID, Long.parseLong(answerCardId + "" + questionType));
            return this;
        }

        public CorrectReportParams.Builder title(String correctTitle){
            this.params.put(CORRECT_TITLE, correctTitle);
            return this;
        }

        public CorrectReportParams.Builder submitTime(Date date){
            this.params.put(SUBMIT_TIME, date);
            return this;
        }

        public CorrectReportParams.Builder answerCardId(long answerCardId){
            this.params.put(ANSWER_CARD_ID, answerCardId);
            return this;
        }

        public CorrectReportParams.Builder questionType(int questionType){
            this.params.put(QUESTION_TYPE, questionType);
            return this;
        }

        public CorrectReportParams.Builder build(){
            return this;
        }
    }
}
