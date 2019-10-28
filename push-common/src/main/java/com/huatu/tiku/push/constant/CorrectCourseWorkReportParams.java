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

    public static final String DETAIL_TYPE = "buyAfterSyllabus";


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

        public CorrectCourseWorkReportParams.Builder bizId(long bizId){
            this.params.put(BIZ_ID, bizId);
            return this;
        }

        public CorrectCourseWorkReportParams.Builder netClass(long netClassId){
            this.params.put(NET_CLASS_ID, netClassId);
            return this;
        }

        public CorrectCourseWorkReportParams.Builder syllabus(long syllabusId){
            this.params.put(SYLLABUS_ID, syllabusId);
            return this;
        }

        public CorrectCourseWorkReportParams.Builder picture(String img){
            this.params.put(PICTURE, img);
            return this;
        }

        public CorrectCourseWorkReportParams.Builder isLive(int isLive){
            this.params.put(IS_LIVE, isLive);
            return this;
        }

        public CorrectCourseWorkReportParams.Builder lesson(long lessonId){
            this.params.put(COURSE_WARE_ID, lessonId);
            return this;
        }


        public CorrectCourseWorkReportParams.Builder build(){
            return this;
        }
    }
}
