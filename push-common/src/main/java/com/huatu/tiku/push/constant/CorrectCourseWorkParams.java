package com.huatu.tiku.push.constant;

/**
 * 描述：申论课后作业参数
 *
 * @author biguodong
 * Create time 2019-02-27 上午10:34
 **/
public abstract class CorrectCourseWorkParams extends Params{

    public static final String TYPE = "correctCourseWork";
    /**
     * 大纲id
     */
    public static final String SYLLABUS_ID = "syllabusId";
    /**
     * 课程id
     */
    public static final String NET_CLASS_ID = "netClassId";

    /**
     * 是否直播 1 直播  0 录播
     */
    public static final String IS_LIVE = "isLive";

    /**
     * 课程封面
     */
    //public static final String IMG = "img";

    /**
     * 课程封面
     */
    public static final String PICTURE = "picture";

    /**
     * 课件 id
     */
    public static final String COURSE_WARE_ID = "lession_id";
}
