package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.UmengNotification;
import com.huatu.tiku.push.cast.strategy.CustomAliasCastStrategyTemplate;
import com.huatu.tiku.push.cast.strategy.NotificationHandler;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;
import com.huatu.tiku.push.constant.FeedBackCorrectParams;
import com.huatu.tiku.push.enums.JumpTargetEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.manager.NoticeLandingManager;
import com.huatu.tiku.push.quartz.factory.FeedBackCastFactory;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.service.api.CorrectFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 下午6:14
 **/

@Slf4j
@Service
public class CorrectFeedbackServiceImpl implements CorrectFeedbackService {


    @Autowired
    private NoticeLandingManager noticeLandingManager;

    @Autowired
    private CustomAliasCastStrategyTemplate customCastStrategyTemplate;

    @Autowired
    private NotificationHandler notificationHandler;
    /**
     * 处理纠错消息通知
     *
     * @param correctFeedbackInfoList
     * @throws BizException
     */
    @Override
    @Async
    public void sendCorrectNotice(List<CorrectFeedbackInfo> correctFeedbackInfoList) throws BizException {
        /**
         * 入库
         */
        correctFeedbackInfoList.forEach(correctFeedbackInfo -> {
            sendCorrectNotice(correctFeedbackInfo);
        });
    }

    /**
     * 处理单个纠错消息通知
     *
     * @param correctFeedbackInfo
     * @throws BizException
     */
    @Override
    public void sendCorrectNotice(CorrectFeedbackInfo correctFeedbackInfo) throws BizException {
        List<NoticeReq> noticeReqList = Lists.newArrayList();
        FeedBackCorrectParams.Builder builder = FeedBackCastFactory.feedbackCorrectParams(correctFeedbackInfo);
        List<NoticeReq.NoticeUserRelation> noticeUserRelations = FeedBackCastFactory.correctNoticeUserRelations(correctFeedbackInfo);
        FeedBackCastFactory.correctNoticeForPush(builder, noticeUserRelations, correctFeedbackInfo, noticeReqList);

        List<UmengNotification> list = FeedBackCastFactory.customCastNotifications(correctFeedbackInfo.getBizId(), noticeReqList, JumpTargetEnum.NOTICE_CENTER);
        noticeLandingManager.insertBatch(noticeReqList);
        customCastStrategyTemplate.setNotificationList(list);
        notificationHandler.setDetailType(NoticeTypeEnum.CORRECT_FEEDBACK);
        notificationHandler.setBizId(correctFeedbackInfo.getBizId());
        notificationHandler.setConcurrent(true);
        notificationHandler.setPushStrategy(customCastStrategyTemplate);
        /**
         * 发送
         */
        log.info("push correct feedback:{}", JSONObject.toJSONString(noticeReqList));
        notificationHandler.push();
    }
}
