package com.huatu.tiku.push.quartz.factory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;
import com.huatu.tiku.push.constant.FeedBackCorrectParams;
import com.huatu.tiku.push.constant.FeedBackSuggestParams;
import com.huatu.tiku.push.constant.SuggestFeedbackInfo;
import com.huatu.tiku.push.enums.DisplayTypeEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 描述：建议反馈& 纠错反馈工厂
 *
 * @author biguodong
 * Create time 2018-11-22 下午5:40
 **/
@Slf4j
public class FeedBackCastFactory extends AbstractFactory{


    private static final String EMPTY_STRING = "null";

    /**
     * 回复内容为空并且有图币
     */
    private static final String NOTICE_EMPTY_WITH_GOLD = "你的题目纠错反馈已收到，我们会尽快修改的。图币奖励在【我的图币】-【账户明细】查看哦~";

    /**
     * 回复内容为空并且没有图币
     */
    private static final String NOTICE_EMPTY_WITHOUT_GOLD = "您的题目纠错反馈已收到，我们会尽快处理的哦~";

    /**
     * feedback correst params builder
     * @param correctFeedbackInfo
     * @return
     */
    public static FeedBackCorrectParams.Builder feedbackCorrectParams(CorrectFeedbackInfo correctFeedbackInfo){
        FeedBackCorrectParams.Builder builder = FeedBackCorrectParams.Builder
                .builder(FeedBackCorrectParams.DETAIL_TYPE)
                .questionId(correctFeedbackInfo.getQuestionId())
                .feedBackId(correctFeedbackInfo.getBizId())
                .build();

        return builder;
    }

    /**
     * feedback suggest params builder
     * @param suggestFeedbackInfo
     * @return
     */
    public static FeedBackSuggestParams.Builder feedbackSuggestParams(SuggestFeedbackInfo suggestFeedbackInfo){
        FeedBackSuggestParams.Builder builder = FeedBackSuggestParams.Builder
                .builder(FeedBackSuggestParams.DETAIL_TYPE)
                .suggestId(suggestFeedbackInfo.getBizId())
                .title(StringUtils.trimToEmpty(suggestFeedbackInfo.getSuggestTitle()) + StringUtils.trimToEmpty(suggestFeedbackInfo.getSuggestContent()))
                .reply(StringUtils.trimToEmpty(suggestFeedbackInfo.getReplyTitle()) + StringUtils.trimToEmpty(suggestFeedbackInfo.getReplyContent()))
                .build();

        return builder;
    }

    /**
     * 推送用户信息--纠错
     * @param correctFeedbackInfo
     * @return
     */
    public static List<NoticeReq.NoticeUserRelation> correctNoticeUserRelations(CorrectFeedbackInfo correctFeedbackInfo){
        List<NoticeReq.NoticeUserRelation> list = Lists.newArrayList();
        list.add(NoticeReq.NoticeUserRelation
                .builder()
                .noticeId(0L)
                .userId(correctFeedbackInfo.getUserId())
                .createTime(correctFeedbackInfo.getDealDate())
                .build());
        return list;
    }

    /**
     * 推送用户信息--建议
     * @param suggestFeedbackInfo
     * @return
     */
    public static List<NoticeReq.NoticeUserRelation> suggestNoticeUserRelations(SuggestFeedbackInfo suggestFeedbackInfo){
        List<NoticeReq.NoticeUserRelation> list = Lists.newArrayList();
        list.add(NoticeReq.NoticeUserRelation
                .builder()
                .noticeId(0L)
                .userId(suggestFeedbackInfo.getUserId())
                .createTime(new Date(suggestFeedbackInfo.getCreateTime() == 0 ? 0L: suggestFeedbackInfo.getCreateTime()))
                .build());
        return list;
    }


    /**
     * 推送消息--纠错
     * @param builder
     * @param noticeUserRelations
     * @param correctFeedbackInfo
     * @param noticeReqList
     */
    public static void correctNoticeForPush(FeedBackCorrectParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations,
                                            CorrectFeedbackInfo correctFeedbackInfo, List<NoticeReq> noticeReqList ){
        List<String> text = Lists.newArrayList();
        if(StringUtils.isEmpty(correctFeedbackInfo.getSource()) || EMPTY_STRING.equals(correctFeedbackInfo.getSource())){
            correctFeedbackInfo.setSource("");
        }else{
            text.add(correctFeedbackInfo.getSource());
        }

        if(StringUtils.isEmpty(correctFeedbackInfo.getReply()) || EMPTY_STRING.equals(correctFeedbackInfo.getReply())){
            correctFeedbackInfo.setReply("");
        }else{
            text.add(correctFeedbackInfo.getReply());
        }

        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.CORRECT_FEEDBACK.getTitle())
                .text(Joiner.on("#").join(text))
                .custom(builder.getParams())
                .type(FeedBackCorrectParams.TYPE)
                .detailType(FeedBackCorrectParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        if(StringUtils.isNoneBlank(noticeReq.getText()) && (null != correctFeedbackInfo.getGold()) && (correctFeedbackInfo.getGold().intValue() > 0)){
            noticeReq.setText(String.format(NoticeTypeEnum.CORRECT_FEEDBACK.getText(), noticeReq.getText()));
        }else if(StringUtils.isBlank(noticeReq.getText())){
            if((null != correctFeedbackInfo.getGold()) && correctFeedbackInfo.getGold().intValue() > 0){
                noticeReq.setText(NOTICE_EMPTY_WITH_GOLD);
            }else{
                noticeReq.setText(NOTICE_EMPTY_WITHOUT_GOLD);
            }
        }
        noticeReqList.add(noticeReq);
    }

    /**
     * 推送消息--建议
     * @param builder
     * @param noticeUserRelations
     * @param suggestFeedbackInfo
     * @param noticeReqList
     */
    public static void suggestNoticeForPush(FeedBackSuggestParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations,
                                            SuggestFeedbackInfo suggestFeedbackInfo, List<NoticeReq> noticeReqList ){
        List<String> text = Lists.newArrayList();
        if(StringUtils.isNotBlank(suggestFeedbackInfo.getSuggestTitle())){
            text.add(suggestFeedbackInfo.getSuggestTitle());
        }
        if(StringUtils.isNotBlank(suggestFeedbackInfo.getSuggestContent())){
            text.add(suggestFeedbackInfo.getReplyContent());
        }
        if(StringUtils.isNotBlank(suggestFeedbackInfo.getReplyTitle())){
            text.add(suggestFeedbackInfo.getReplyTitle());
        }
        if(StringUtils.isNotBlank(suggestFeedbackInfo.getReplyContent())){
            text.add(suggestFeedbackInfo.getReplyContent());
        }

        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.SUGGEST_FEEDBACK.getTitle())
                .text(Joiner.on("#").join(text))
                .custom(builder.getParams())
                .type(FeedBackSuggestParams.TYPE)
                .detailType(FeedBackSuggestParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        noticeReqList.add(noticeReq);
    }


}
