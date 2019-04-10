package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.UmengNotification;
import com.huatu.tiku.push.cast.strategy.CustomAliasCastStrategyTemplate;
import com.huatu.tiku.push.cast.strategy.NotificationHandler;
import com.huatu.tiku.push.constant.FeedBackSuggestParams;
import com.huatu.tiku.push.constant.SuggestFeedbackInfo;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.manager.NoticeLandingManager;
import com.huatu.tiku.push.quartz.factory.FeedBackCastFactory;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.service.api.SuggestFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-14 下午1:27
 **/
@Slf4j
@Service
public class SuggestFeedbackServiceImpl implements SuggestFeedbackService{

    @Autowired
    private NoticeLandingManager noticeLandingManager;

    @Autowired
    private CustomAliasCastStrategyTemplate customCastStrategyTemplate;

    @Autowired
    private NotificationHandler notificationHandler;

    /**
     * 处理单个纠错消息通知
     *
     * @param suggestFeedbackInfo
     * @throws BizException
     */
    @Override
    public void sendSuggestNotice(SuggestFeedbackInfo suggestFeedbackInfo) throws BizException {
        List<NoticeReq> noticeReqList = Lists.newArrayList();
        FeedBackSuggestParams.Builder builder = FeedBackCastFactory.feedbackSuggestParams(suggestFeedbackInfo);
        List<NoticeReq.NoticeUserRelation> noticeUserRelations = FeedBackCastFactory.suggestNoticeUserRelations(suggestFeedbackInfo);
        FeedBackCastFactory.suggestNoticeForPush(builder, noticeUserRelations, suggestFeedbackInfo, noticeReqList);

        List<UmengNotification> list = FeedBackCastFactory.customCastNotifications(noticeReqList);
        noticeLandingManager.insertBatch(noticeReqList);
        customCastStrategyTemplate.setNotificationList(list);
        notificationHandler.setDetailType(NoticeTypeEnum.SUGGEST_FEEDBACK);
        notificationHandler.setBizId(suggestFeedbackInfo.getBizId());
        notificationHandler.setConcurrent(false);
        notificationHandler.setPushStrategy(customCastStrategyTemplate);
        /**
         * 发送
         */
        log.info("push suggest feedback:{}", JSONObject.toJSONString(noticeReqList));
        notificationHandler.push();
    }
}
