package com.huatu.tiku.push.quartz.factory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.UmengNotification;
import com.huatu.tiku.push.constant.*;
import com.huatu.tiku.push.enums.DisplayTypeEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 描述：申论批改工厂方法
 *
 * @author biguodong
 * Create time 2019-08-01 10:13 PM
 **/
@Slf4j
public class CorrectFactory extends AbstractFactory{



    /**
     * correct params builder
     * @param correctReturnInfo
     * @return
     */
    public static CorrectReturnParams.Builder correctReturnParams(CorrectReturnInfo correctReturnInfo){
        CorrectReturnParams.Builder builder = CorrectReturnParams.Builder
                .builder(CorrectReturnParams.DETAIL_TYPE)
                .answerCardId(correctReturnInfo.getAnswerCardId())
                .questionType(correctReturnInfo.getQuestionType())
                .returnContent(correctReturnInfo.getReturnContent())
                .userId(correctReturnInfo.getUserId())
                .build();

        return builder;
    }

    public static CorrectReportParams.Builder correctReportParams(CorrectReportInfo correctReportInfo){
        CorrectReportParams.Builder builder = CorrectReportParams.Builder
                .builder(CorrectReturnParams.DETAIL_TYPE)
                .answerCardId(correctReportInfo.getAnswerCardId())
                .questionType(correctReportInfo.getQuestionType())
                .userId(correctReportInfo.getUserId())
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
     * 推送消息--退回
     * @param builder
     * @param noticeUserRelations
     * @param correctReturnInfo
     * @param noticeReqList
     */
    public static void correctReuturnNoticeForPush(CorrectReturnParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations,
                                            CorrectReturnInfo correctReturnInfo, List<NoticeReq> noticeReqList ){

        StringBuilder noticeText = new StringBuilder(correctReturnInfo.getReturnContent());


        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.CORRECT_RETURN.getTitle())
                .text(noticeText.toString())
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


        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.CORRECT_REPORT.getTitle())
                .text(NoticeTypeEnum.CORRECT_REPORT.getText())
                .custom(builder.getParams())
                .type(CorrectParams.TYPE)
                .detailType(CorrectReportParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        noticeReqList.add(noticeReq);
    }
}
