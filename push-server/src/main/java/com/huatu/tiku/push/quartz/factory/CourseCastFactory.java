package com.huatu.tiku.push.quartz.factory;

import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.CourseParams;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-22 下午8:17
 **/

@Slf4j
public class CourseCastFactory extends AbstractFactory{


    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    /**
     * 课程消息获取用户关系数据
     * @param users
     * @return
     */
    public static List<NoticeReq.NoticeUserRelation> noticeRelation(Set<Integer> users){
        if(CollectionUtils.isEmpty(users)){
            throw new BizException(NoticePushErrors.NOTICE_USER_LIST_EMPTY);
        }
        List<NoticeReq.NoticeUserRelation> userRelations = Lists.newArrayList();
        users.forEach(item->{
            int userIdSource = item.intValue();
            Long userId = Long.valueOf(userIdSource);
            NoticeReq.NoticeUserRelation noticeUserRelation =  NoticeReq.NoticeUserRelation.builder()
                    .userId(userId)
                    .build();
            userRelations.add(noticeUserRelation);
        });

        return userRelations;
    }

    /**
     * 课程信息builder
     * @param courseInfo
     * @return
     */
    public static CourseParams.Builder courseParams(CourseInfo courseInfo){
        CourseParams.Builder builder = CourseParams.Builder
                .builder()
                .courseId(String.valueOf(courseInfo.getClassId()))
                .teacher(courseInfo.getTeacher())
                .picture(courseInfo.getClassImg())
                .startTime(String.valueOf(courseInfo.getStartTime().getTime()))
                .classTitle(courseInfo.getClassTitle())
                .build();

        return builder;
    }


    /**
     * remind 消息推送体
     * @return
     */
    public static List<NoticeReq> noticeRemindPush(CourseInfo courseInfo, CourseParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeRelations){
        List<NoticeReq> noticePushList = Lists.newArrayList();
        String alert = simpleDateFormat.format(new Date(courseInfo.getStartTime().getTime()));
        String title = String.format(NoticeTypeEnum.COURSE_REMIND.getTitle(), alert);

        String pushText = NoticeTypeEnum.COURSE_REMIND.getText();
        pushText = String.format(pushText, courseInfo.getTeacher());

        NoticeReq noticeForPush = NoticeReq.builder()
                .type(CourseParams.TYPE)
                .detailType(CourseParams.REMIND)
                .title(title)
                .text(pushText)
                .custom(builder.getParams())
                .users(noticeRelations)
                .build();
        noticePushList.add(noticeForPush);
        return  noticePushList;

    }

    /**
     * remind 消息推送体
     * @return
     */
    public static NoticeReq noticeRemindPush(CourseInfo courseInfo, CourseParams.Builder builder){
        String alert = simpleDateFormat.format(new Date(courseInfo.getStartTime().getTime()));
        String title = String.format(NoticeTypeEnum.COURSE_REMIND.getTitle(), alert);

        String pushText = NoticeTypeEnum.COURSE_REMIND.getText();
        pushText = String.format(pushText, courseInfo.getTeacher());

        NoticeReq noticeForPush = NoticeReq.builder()
                .type(CourseParams.TYPE)
                .detailType(CourseParams.REMIND)
                .title(title)
                .text(pushText)
                .custom(builder.getParams())
                .build();
        return  noticeForPush;

    }

    /**
     * ready 消息推送体
     * @return
     */
    public static List<NoticeReq> noticeReadyPush(CourseInfo courseInfo, CourseParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeRelations){
        List<NoticeReq> noticePushList = Lists.newArrayList();
        NoticeReq noticeForPush = NoticeReq.builder()
                .type(CourseParams.TYPE)
                .detailType(CourseParams.READY)
                .title(NoticeTypeEnum.COURSE_READY.getTitle())
                .text(courseInfo.getSection())
                .custom(builder.getParams())
                .users(noticeRelations)
                .build();
        noticePushList.add(noticeForPush);
        return  noticePushList;
    }


    /**
     * ready 消息推送体
     * @return
     */
    public static NoticeReq noticeReadyPush(CourseInfo courseInfo, CourseParams.Builder builder){
        NoticeReq noticeForPush = NoticeReq.builder()
                .type(CourseParams.TYPE)
                .detailType(CourseParams.READY)
                .title(NoticeTypeEnum.COURSE_READY.getTitle())
                .text(courseInfo.getSection())
                .custom(builder.getParams())
                .build();
        return  noticeForPush;
    }


    /**
     * 消息保存体
     * @param courseInfo
     * @param builder
     * @param noticeRelations
     * @return
     */
    public static List<NoticeReq> noticeRemindInsert(CourseInfo courseInfo, CourseParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeRelations){
        String alert = simpleDateFormat.format(new Date(courseInfo.getStartTime().getTime()));
        String title = String.format(NoticeTypeEnum.COURSE_REMIND.getTitle(), alert);
        List<NoticeReq> noticeInsertList = Lists.newArrayList();
        NoticeReq noticeForInsert = NoticeReq.builder()
                .type(CourseParams.TYPE)
                .detailType(CourseParams.REMIND)
                .title(title)
                .text(courseInfo.getClassTitle())
                .custom(builder.getParams())
                .users(noticeRelations)
                .build();
        noticeInsertList.add(noticeForInsert);
        return noticeInsertList;
    }




    /**
     * Ready 消息保存体
     * @param courseInfo
     * @param builder
     * @param noticeRelations
     * @return
     */
    public static List<NoticeReq> noticeReadyInsert(CourseInfo courseInfo, CourseParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeRelations){
        List<NoticeReq> noticeInsertList = Lists.newArrayList();
        NoticeReq noticeForInsert = NoticeReq.builder()
                .type(CourseParams.TYPE)
                .detailType(CourseParams.READY)
                .title(NoticeTypeEnum.COURSE_READY.getTitle())
                .text(courseInfo.getSection())
                .custom(builder.getParams())
                .users(noticeRelations)
                .build();
        noticeInsertList.add(noticeForInsert);
        return noticeInsertList;
    }






}
