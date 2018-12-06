package com.huatu.tiku.push.constant;

import com.google.common.base.Joiner;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-11 下午5:08
 **/
public class NoticePushRedisKey {

    public static final int PUSH_RESULT_EXPIRE_TIME = 7;

    public static final String SELF_SEPARATOR = "#";

    private static final String COURSE_ORDER_USER_PRE = "notice.push.course.order.users";

    private static final String COURSE_CLASS_ID = "notice.push.class";

    private static final String COURSE_FILE_ID = "notice.push.file.upload";

    private static final String NOTICE_PUSH_RESULT = "notice.push.result";



    /**
     * 获取购买课程的用户redis key
     * @param classId
     * @return
     */
    public static String getOrderUserKey(String classId){
        return Joiner.on(".").join(COURSE_ORDER_USER_PRE, classId);
    }

    /**
     * 获取直播课redis key
     * 存储该直播课下报名的用户
     * @param bizId
     * @return
     */
    public static String getCourseClassId(long bizId){
        return Joiner.on(".").join(COURSE_CLASS_ID, bizId);
    }

    /**
     * 获取直播课推送文件id
     * @return
     */
    public static String getCourseFileId(String classId){
        return Joiner.on(".").join(COURSE_FILE_ID, classId);
    }

    /**
     * 获取notice push result hash key
     * @param resultId
     * @param detailType
     * @param type
     * @return
     */
    public static String getPushResultKey(String type, String detailType, String resultId){
        return Joiner.on(".").join(NOTICE_PUSH_RESULT, type, detailType, resultId);
    }
}
