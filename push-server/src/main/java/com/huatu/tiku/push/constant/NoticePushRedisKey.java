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

    private static final String COURSE_LIVE_ID = "notice.push.live.id";

    private static final String COURSE_FILE_ID = "notice.push.file.upload";

    private static final String NOTICE_PUSH_RESULT = "notice.push.result";

    private static final String DATA_MIGRATE_PAGE_NUM = "notice.push.migrate.page";

    private static final String DATA_MIGRATE_SIZE_COUNT = "notice.push.migrate.size";

    private static final String DATA_MIGRATE_NOTICE_CREATE_TIME = "notice.push.migrate.notice.create_time";

    private static final String NOTICE_ENTITY_KEY = "notice_entity_$%s";


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
     * 直播课 id 推送判重处理
     * @param liveId
     * @return
     */
    public static String getCourseLiveId(String type, String detailType, long liveId){
        return Joiner.on(".").join(COURSE_LIVE_ID, type, detailType, liveId);
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

    /**
     * 获取数据转移页码
     * @return
     */
    public static String getDataMigratePageNum(){
        return DATA_MIGRATE_PAGE_NUM;
    }


    /**
     * 获取数据转移每页大小
     * @return
     */
    public static String getDataMigrateSizeCount(){
        return DATA_MIGRATE_SIZE_COUNT;
    }

    /**
     * notice entity 创建时间数据转移key
     * @return
     */
    public static String getDataMigrateNoticeCreateTime(){
        return DATA_MIGRATE_NOTICE_CREATE_TIME;
    }

    /**
     * notice Id 缓存
     * @param noticeId
     * @return
     */
    public static String getNoticeEntityKey(long noticeId){
        return String.format(NOTICE_ENTITY_KEY, noticeId);
    }
}
