package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午10:34
 **/
public class CorrectReportParams extends CorrectParams{

    public static final String DETAIL_TYPE = "manualReport";

    private static final String TOPIC_TYPE = "topicType";

    private static final String ANSWER_CARD_ID = "answerCardId";

    private static final String PAPER_NAME = "paperName";

    private static final String AREA_NAME = "areaName";

    private static final String PAPER_ID = "paperId";

    private static final String QUESTION_BASE_ID = "questionBaseId";



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

        public static CorrectReportParams.Builder builder(){
            CorrectReportParams.Builder builder = new CorrectReportParams.Builder();
            return builder;
        }

        public CorrectReportParams.Builder topicType(int topicType){
            this.params.put(TOPIC_TYPE, topicType);
            return this;
        }

        public CorrectReportParams.Builder bizId(long bizId){
            this.params.put(BIZ_ID, bizId);
            return this;
        }

        public CorrectReportParams.Builder answerCardId(long answerCardId){
            this.params.put(ANSWER_CARD_ID, answerCardId);
            return this;
        }

        public CorrectReportParams.Builder paperName(String paperName){
            this.params.put(PAPER_NAME, paperName);
            return this;
        }

        public CorrectReportParams.Builder areaName(String areaName){
            this.params.put(AREA_NAME, areaName);
            return this;
        }

        public CorrectReportParams.Builder paperId(long paperId){
            this.params.put(PAPER_ID, paperId);
            return this;
        } public CorrectReportParams.Builder questionBaseId(long questionBaseId){
            this.params.put(QUESTION_BASE_ID, questionBaseId);
            return this;
        }

        public CorrectReportParams.Builder build(){
            return this;
        }
    }
}
