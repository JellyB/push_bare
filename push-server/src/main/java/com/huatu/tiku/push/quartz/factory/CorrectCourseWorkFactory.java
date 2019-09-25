package com.huatu.tiku.push.quartz.factory;

import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.*;
import com.huatu.tiku.push.enums.DisplayTypeEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 描述：申论课后作业批改工厂方法
 *
 * @author biguodong
 * Create time 2019-08-01 10:13 PM
 **/
@Slf4j
public class CorrectCourseWorkFactory extends AbstractFactory{


    /**
     * correct course work return params builder
     * @param pushInfo
     * @return
     */
    public static CorrectCourseWorkReturnParams.Builder returnParams(CorrectCourseWorkPushInfo pushInfo){
        CorrectCourseWorkReturnParams.Builder builder = CorrectCourseWorkReturnParams.Builder
                .builder()
                .netClass(pushInfo.getNetClassId())
                .syllabus(pushInfo.getSyllabusId())
                .img(pushInfo.getImg())
                .build();
        return builder;
    }

    /**
     * correct course work report params builder
     * @param pushInfo
     * @return
     */
    public static CorrectCourseWorkReportParams.Builder reportParams(CorrectCourseWorkPushInfo pushInfo){
        CorrectCourseWorkReportParams.Builder builder = CorrectCourseWorkReportParams.Builder
                .builder()
                .netClass(pushInfo.getNetClassId())
                .syllabus(pushInfo.getSyllabusId())
                .img(pushInfo.getImg())
                .build();

        return builder;
    }


    /**
     * 推送用户 - 退回 & 报告
     * @param pushInfo
     * @return
     */
    public static List<NoticeReq.NoticeUserRelation> correctCourseWorkRelations(CorrectCourseWorkPushInfo pushInfo){
        List<NoticeReq.NoticeUserRelation> list = Lists.newArrayList();
        list.add(NoticeReq.NoticeUserRelation
                .builder()
                .noticeId(pushInfo.getBizId())
                .userId(pushInfo.getUserId())
                .createTime(new Date())
                .build());
        return list;
    }

    /**
     * 推送消息--申论课后作业被退回
     * @param builder
     * @param noticeUserRelations
     * @param pushInfo
     * @param noticeReqList
     */
    public static void noticeReturn4Push(CorrectCourseWorkReturnParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations, CorrectCourseWorkPushInfo pushInfo, List<NoticeReq> noticeReqList ){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        String date = dateFormat.format(pushInfo.getSubmitTime());
        String text4Push = StringUtils.EMPTY;
        String text4Data = String.format(NoticeTypeEnum.CORRECT_RETURN_COURSE_WORK.getText4Data(), date, pushInfo.getStem(), pushInfo.getReturnContent());


        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.CORRECT_RETURN.getTitle())
                .text4Push(text4Push)
                .text4Data(text4Data)
                .custom(builder.getParams())
                .type(CorrectCourseWorkParams.TYPE)
                .detailType(CorrectCourseWorkReturnParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        noticeReqList.add(noticeReq);
    }


    /**
     * 推送消息--申论课后作业出报告
     * @param builder
     * @param noticeUserRelations
     * @param pushInfo
     * @param noticeReqList
     */
    public static void noticeReport4Push(CorrectCourseWorkReportParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations,
                                         CorrectCourseWorkPushInfo pushInfo, List<NoticeReq> noticeReqList ){


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        String date = dateFormat.format(pushInfo.getSubmitTime() == null ? new Date() : pushInfo.getSubmitTime());
        String text4Data = String.format(NoticeTypeEnum.CORRECT_REPORT_COURSE_WORK.getText4Data(), date, pushInfo.getStem());

        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.CORRECT_REPORT_COURSE_WORK.getTitle())
                .text4Push(null)
                .text4Data(text4Data)
                .custom(builder.getParams())
                .type(CorrectCourseWorkParams.TYPE)
                .detailType(CorrectCourseWorkReportParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        noticeReqList.add(noticeReq);
    }
}
