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
public class CorrectFactory extends AbstractFactory{



    /**
     * correct return params builder
     * @param correctReturnInfo
     * @return
     */
    public static CorrectReturnParams.Builder correctReturnParams(CorrectReturnInfo correctReturnInfo){
        CorrectReturnParams.Builder builder = CorrectReturnParams.Builder
                .builder()
                .topicType(correctReturnInfo.getTopicType())
                .build();

        return builder;
    }

    /**
     * correct report params builder
     * @param correctReportInfo
     * @return
     */
    public static CorrectReportParams.Builder correctReportParams(CorrectReportInfo correctReportInfo){
        CorrectReportParams.Builder builder = CorrectReportParams.Builder
                .builder()
                .answerCardId(correctReportInfo.getAnswerCardId())
                .areaName(correctReportInfo.getAreaName())
                .paperName(correctReportInfo.getPaperName())
                .topicType(correctReportInfo.getTopicType())
                .paperId(correctReportInfo.getPaperId())
                .questionBaseId(correctReportInfo.getQuestionBaseId())
                .build();

        return builder;
    }


    /**
     * 推送用户 - 退回
     * @param correctReturnInfo
     * @return
     */
    public static List<NoticeReq.NoticeUserRelation> correctReturnUserRelations(CorrectReturnInfo correctReturnInfo){
        List<NoticeReq.NoticeUserRelation> list = Lists.newArrayList();
        list.add(NoticeReq.NoticeUserRelation
                .builder()
                .noticeId(0L)
                .userId(correctReturnInfo.getUserId())
                .createTime(correctReturnInfo.getDealDate())
                .build());
        return list;
    }

    /**
     * 推送用户 - 报告
     * @param correctReportInfo
     * @return
     */
    public static List<NoticeReq.NoticeUserRelation> correctReportUserRelations(CorrectReportInfo correctReportInfo){
        List<NoticeReq.NoticeUserRelation> list = Lists.newArrayList();
        list.add(NoticeReq.NoticeUserRelation
                .builder()
                .noticeId(0L)
                .userId(correctReportInfo.getUserId())
                .createTime(correctReportInfo.getDealDate())
                .build());
        return list;
    }

    /**
     * 推送消息--申论 批改 退回
     * @param builder
     * @param noticeUserRelations
     * @param correctReturnInfo
     * @param noticeReqList
     */
    public static void correctReturnNoticeForPush(CorrectReturnParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations, CorrectReturnInfo correctReturnInfo, List<NoticeReq> noticeReqList ){

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
