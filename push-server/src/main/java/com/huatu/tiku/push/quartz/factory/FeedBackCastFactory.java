package com.huatu.tiku.push.quartz.factory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;
import com.huatu.tiku.push.constant.FeedBackCorrectParams;
import com.huatu.tiku.push.constant.FeedBackSuggestParams;
import com.huatu.tiku.push.constant.SuggestFeedbackInfo;
import com.huatu.tiku.push.enums.CorrectDealEnum;
import com.huatu.tiku.push.enums.DisplayTypeEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
    private static final String CORRECT_SOURCE = "#%s#";

    /**
     * 回复内容为空并且有图币
     */
    @Deprecated
    private static final String NOTICE_EMPTY_WITH_GOLD = "你的题目纠错反馈已收到，我们会尽快修改的。图币奖励在【我的图币】-【账户明细】查看哦~";

    /**
     * 纠错忽略
     */
    @Deprecated
    private static final String NOTICE_IGNORE = "您于%s提交的试题纠错已被查收，感谢您的反馈，经过题库组小伙伴认真核实后，认为该题没有错误，请您仔细审题，当心题目中的陷阱哦~";

    /**
     * 回复内容为空并且没有图币
     */
    @Deprecated
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
                .source(StringUtils.trimToEmpty(correctFeedbackInfo.getSource()))
                .reply(StringUtils.trimToEmpty(correctFeedbackInfo.getReply()))
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
                .title(StringUtils.trimToEmpty(suggestFeedbackInfo.getReplyTitle()))
                .reply(StringUtils.trimToEmpty(suggestFeedbackInfo.getReplyContent()))
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
        CorrectDealEnum status = correctFeedbackInfo.getStatus() == null ? CorrectDealEnum.NORMAL : correctFeedbackInfo.getStatus();
        Date dealDate = correctFeedbackInfo.getDealDate() == null ? new Date() : correctFeedbackInfo.getDealDate();
        String replyTime = NoticeTimeParseUtil.localDateFormat.format(dealDate);
        StringBuffer noticeText = new StringBuffer();
        if(StringUtils.isNotEmpty(correctFeedbackInfo.getSource()) && !EMPTY_STRING.equals(correctFeedbackInfo.getSource())){
            noticeText.append(String.format(CORRECT_SOURCE, correctFeedbackInfo.getSource()));
        }
        switch (status){
            case IGNORE:
                dealIgnoreResponseText(noticeText, replyTime);
                break;
            case NORMAL:
                dealNormalResponseText(correctFeedbackInfo, noticeText, replyTime);
                break;
                default:
                    break;
        }

        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.CORRECT_FEEDBACK.getTitle())
                .text(noticeText.toString())
                .custom(builder.getParams())
                .type(FeedBackCorrectParams.TYPE)
                .detailType(FeedBackCorrectParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        noticeReqList.add(noticeReq);
    }

    /**
     * 处理正常回复
     * @param correctFeedbackInfo
     * @param text
     */
    private static void dealNormalResponseText(CorrectFeedbackInfo correctFeedbackInfo, StringBuffer text, String replyTime) {
        /**
         * 有金币
         */
        if(null != correctFeedbackInfo.getGold() && correctFeedbackInfo.getGold() > 0){
            /**
             * 回复内容不为空
             */
            if(StringUtils.isNotEmpty(correctFeedbackInfo.getReply()) && !EMPTY_STRING.equals(correctFeedbackInfo.getReply())){
                String reply = correctFeedbackInfo.getReply();
                text.append(String.format(CorrectText.WITH_REPLY_WITH_GOLD.getText(), replyTime, reply));
            }else{
                text.append(String.format(CorrectText.WITHOUT_REPLY_WITH_GOLD.getText(), replyTime));
            }
            /**
             * 无金币
             */
        }else{
            /**
             * 回复内容不为空
             */
            if(StringUtils.isNotEmpty(correctFeedbackInfo.getReply()) && !EMPTY_STRING.equals(correctFeedbackInfo.getReply())){
                String reply = correctFeedbackInfo.getReply();
                text.append(String.format(CorrectText.WITH_REPLY_WITHOUT_GOLD.getText(), replyTime, reply));
                /**
                 * 没有回复内容
                 */
            }else{
                text.append(String.format(CorrectText.WITHOUT_REPLY_WITHOUT_GOLD.getText(), replyTime));
            }
        }
    }

    /**
     * 处理忽略文案
     * @param text
     * @param replyTime
     */
    private static void dealIgnoreResponseText(StringBuffer text, String replyTime) {
        text.append(String.format(CorrectText.IGNORE.getText(), replyTime));
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
        if(StringUtils.isNotBlank(suggestFeedbackInfo.getReplyTitle())){
            text.add(suggestFeedbackInfo.getReplyTitle());
        }
        if(StringUtils.isNotBlank(suggestFeedbackInfo.getReplyContent())){
            text.add(suggestFeedbackInfo.getReplyContent());
        }
        String replyContent = Joiner.on("#").join(text);
        long time = suggestFeedbackInfo.getCreateTime() == 0 ? System.currentTimeMillis() : suggestFeedbackInfo.getCreateTime();
        String replyTime = NoticeTimeParseUtil.dayTimeDateFormat.format(new Date(time));

        NoticeReq noticeReq = NoticeReq.builder()
                .title(NoticeTypeEnum.SUGGEST_FEEDBACK.getTitle())
                .text(String.format(NoticeTypeEnum.SUGGEST_FEEDBACK.getText(), replyTime, replyContent))
                .custom(builder.getParams())
                .type(FeedBackSuggestParams.TYPE)
                .detailType(FeedBackSuggestParams.DETAIL_TYPE)
                .displayType(DisplayTypeEnum.MESSAGE.getType())
                .users(noticeUserRelations)
                .build();
        noticeReqList.add(noticeReq);
    }



    @Getter
    @AllArgsConstructor
    public enum CorrectText{
        IGNORE("您于%s提交的试题纠错已被查收，感谢您的反馈，经过题库组小伙伴认真核实后，认为该道题没有错误，请您仔细审题，当心题目中的陷阱哦~"),
        WITHOUT_REPLY_WITHOUT_GOLD("您于%s提交的试题纠错已被查收，感谢您的反馈，经过题库组小伙伴认真核实后，认为该道题没有错误，请您仔细审题，当心题目中的陷阱哦~"),
        WITH_REPLY_WITH_GOLD("您于%s提交的试题纠错已被查收，感谢您的反馈，以下为老师回复：%s。图币奖励在【我的图币】-【账户明细】查看哦~"),
        WITHOUT_REPLY_WITH_GOLD("您于%s提交的试题纠错已被查收，感谢您的反馈，我们会尽快修改的。图币奖励在【我的图币】-【账户明细】查看哦~"),
        WITH_REPLY_WITHOUT_GOLD("您于%s提交的试题纠错已被查收，感谢您的反馈，以下为老师回复：%s");
        private String text;
    }
}
