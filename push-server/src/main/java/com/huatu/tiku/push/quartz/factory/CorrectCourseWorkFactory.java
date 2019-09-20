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
 * 描述：申论批改工厂方法
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
    public static CorrectCourseWorkReturnParams.Builder returnParmas(CorrectCourseWorkPushInfo pushInfo){
        CorrectCourseWorkReturnParams.Builder builder = CorrectCourseWorkReturnParams.Builder
                .builder()
                .netClass(pushInfo.getNetClassId())
                .syllabus(pushInfo.getSyllabusId())
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
     * 推送消息--申论 批改 退回
     * @param builder
     * @param noticeUserRelations
     * @param pushInfo
     * @param noticeReqList
     */
    public static void correctReturnNoticeForPush(CorrectCourseWorkReturnParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations, CorrectCourseWorkPushInfo pushInfo, List<NoticeReq> noticeReqList ){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        String date = dateFormat.format(correctReturnInfo.getSubmitTime());
        String text4Push = StringUtils.EMPTY;
        String text4Data = String.format(NoticeTypeEnum.CORRECT_RETURN.getText4Data(), date, correctReturnInfo.getReturnContent());


        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.CORRECT_RETURN.getTitle())
                .text4Push(text4Push)
                .text4Data(text4Data)
                .custom(builder.getParams())
                .type(CorrectParams.TYPE)
                .detailType(CorrectReturnParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        noticeReqList.add(noticeReq);
    }


    /**
     * 推送消息--退回
     * @param builder
     * @param noticeUserRelations
     * @param correctReportInfo
     * @param noticeReqList
     */
    public static void correctReportNoticeForPush(CorrectReportParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations,
                                                   CorrectReportInfo correctReportInfo, List<NoticeReq> noticeReqList ){


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        String date = dateFormat.format(correctReportInfo.getSubmitTime() == null ? new Date() : correctReportInfo.getSubmitTime());
        String text4Data = String.format(NoticeTypeEnum.CORRECT_REPORT.getText4Data(), date, correctReportInfo.getQuestionName());

        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.CORRECT_REPORT.getTitle())
                .text4Push(null)
                .text4Data(text4Data)
                .custom(builder.getParams())
                .type(CorrectParams.TYPE)
                .detailType(CorrectReportParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        noticeReqList.add(noticeReq);
    }
}
