package com.huatu.tiku.push.quartz.factory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;
import com.huatu.tiku.push.constant.FeedBackParams;
import com.huatu.tiku.push.enums.DisplayTypeEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 描述：
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
     * feedback params builder
     * @param correctFeedbackInfo
     * @return
     */
    public static FeedBackParams.Builder feedbackParams(CorrectFeedbackInfo correctFeedbackInfo){
        FeedBackParams.Builder builder = FeedBackParams.Builder
                .builder(FeedBackParams.CORRECT)
                .questionId(correctFeedbackInfo.getQuestionId())
                .feedBackId(correctFeedbackInfo.getBizId())
                .build();

        return builder;
    }

    /**
     * 推送用户信息
     * @param correctFeedbackInfo
     * @return
     */
    public static List<NoticeReq.NoticeUserRelation> noticeUserRelations(CorrectFeedbackInfo correctFeedbackInfo){
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
     * 推送消息
     * @param builder
     * @param noticeUserRelations
     * @param correctFeedbackInfo
     * @param noticeReqList
     */
    public static void noticeForPush(FeedBackParams.Builder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations,
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
                .type(FeedBackParams.TYPE)
                .detailType(FeedBackParams.CORRECT)
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
}
