package com.huatu.tiku.push.constant;



import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-07 下午2:11
 **/
public class CourseParams extends Params {

    public static final String TYPE = "course";

    public static final String REMIND = "remind";

    public static final String READY = "ready";

    private static final String PICTURE = "picture";

    public static final String TEACHER = "teacher";

    public static final String START_TIME = "startTime";

    public static final String CLASS_TITLE = "classTitle";
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

        public static Builder builder(){
            return new Builder();
        }

        public Builder bizId(long bizId){
            this.params.put(BIZ_ID, bizId);
            return this;
        }

        public Builder courseId(String courseId){
            this.params.put(BIZ_ID, courseId);
            return this;
        }

        public Builder picture(String picture){
            this.params.put(PICTURE, picture);
            return this;
        }

        public Builder teacher(String teacher){
            this.params.put(TEACHER, teacher);
            return this;
        }
        public Builder startTime(String startTime){
            this.params.put(START_TIME, startTime);
            return this;
        }
        public Builder classTitle(String classTitle){
            this.params.put(CLASS_TITLE, classTitle);
            return this;
        }

        public Builder build(){
            return this;
        }
    }
}
