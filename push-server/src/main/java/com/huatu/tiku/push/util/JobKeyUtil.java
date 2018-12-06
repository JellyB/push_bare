package com.huatu.tiku.push.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.huatu.tiku.push.enums.NoticeTypeEnum;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-12 下午1:13
 **/
public class JobKeyUtil {

    private static final List<String> NOTICE_GROUP = Lists.newArrayList();

    static{
        NOTICE_GROUP.add(triggerGroup(NoticeTypeEnum.COURSE_REMIND));
        NOTICE_GROUP.add(triggerGroup(NoticeTypeEnum.COURSE_READY));
    }

    private static final String JOB_SEPARATOR = "_";
    /**
     * job name
     * @param noticeTypeEnum
     * @param classId
     * @return
     */
    public static String jobName(NoticeTypeEnum noticeTypeEnum, String classId){
        return Joiner.on(JOB_SEPARATOR).join(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType(), classId);
    }

    /**
     * job name
     * @param noticeTypeEnum
     * @param extra
     * @param classId
     * @return
     */
    public static String jobName(NoticeTypeEnum noticeTypeEnum, String extra, String classId){
        return Joiner.on(JOB_SEPARATOR).join(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType(), extra, classId);
    }

    /**
     * job group
     * @param noticeTypeEnum
     * @return
     */
    public static String jobGroup(NoticeTypeEnum noticeTypeEnum){
        return Joiner.on(JOB_SEPARATOR).join(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType());

    }

    /**
     * jobGroup
     * @param noticeTypeEnum
     * @param extra
     * @return
     */
    public static String jobGroup(NoticeTypeEnum noticeTypeEnum, String extra){
        return Joiner.on(JOB_SEPARATOR).join(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType(), extra);
    }

    /**
     * trigger name
     * @param noticeTypeEnum
     * @param classId
     * @return
     */
    public static String triggerName(NoticeTypeEnum noticeTypeEnum, String classId){
        return Joiner.on(JOB_SEPARATOR).join(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType(), classId);
    }

    /**
     * tigger name
     * @param noticeTypeEnum
     * @param extra
     * @param classId
     * @return
     */
    public static String triggerName(NoticeTypeEnum noticeTypeEnum, String extra, String classId){
        return Joiner.on(JOB_SEPARATOR).join(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType(), extra, classId);
    }

    /**
     * trigger group
     * @param noticeTypeEnum
     * @return
     */
    public static String triggerGroup(NoticeTypeEnum noticeTypeEnum){
        return Joiner.on(JOB_SEPARATOR).join(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType());
    }

    /**
     * trigger group
     * @param noticeTypeEnum
     * @param extra
     * @return
     */
    public static String triggerGroup(NoticeTypeEnum noticeTypeEnum, String extra){
        return Joiner.on(JOB_SEPARATOR).join(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType(), extra);
    }


    /**
     * 判断当前组是否需要钉钉link通知
     * @param triggerGroup
     * @return
     */
    public static boolean checkTriggerKey(String triggerGroup){
        return NOTICE_GROUP.contains(triggerGroup);
    }

    /**
     * 拆分-获取处查询param
     * @param triggerName
     * @return
     */
    public static Object[] obtainLinkParams(String triggerName){
        return triggerName.split(JOB_SEPARATOR);
    }
}
