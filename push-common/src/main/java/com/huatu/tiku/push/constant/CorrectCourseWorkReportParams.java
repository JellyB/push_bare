package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午10:34
 **/
public class CorrectCourseWorkReportParams extends CorrectCourseWorkParams{

    public static final String DETAIL_TYPE = "courseWorkReport";


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

        public static CorrectCourseWorkReportParams.Builder builder(){
            CorrectCourseWorkReportParams.Builder builder = new CorrectCourseWorkReportParams.Builder();
            return builder;
        }

        public CorrectCourseWorkReportParams.Builder netClass(int netClassId){
            this.params.put(NET_CLASS_ID, netClassId);
            return this;
        }

        public CorrectCourseWorkReportParams.Builder syllabus(long syllabusId){
            this.params.put(SYLLABUS_ID, syllabusId);
            return this;
        }
        public CorrectCourseWorkReportParams.Builder build(){
            return this;
        }
    }
}
