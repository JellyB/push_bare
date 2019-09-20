package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.UmengNotification;
import com.huatu.tiku.push.cast.strategy.CustomAliasCastStrategyTemplate;
import com.huatu.tiku.push.cast.strategy.NotificationHandler;
import com.huatu.tiku.push.constant.CorrectCourseWorkPushInfo;
import com.huatu.tiku.push.constant.CorrectReturnParams;
import com.huatu.tiku.push.enums.JumpTargetEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.manager.NoticeLandingManager;
import com.huatu.tiku.push.quartz.factory.CorrectFactory;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-09-20 2:03 PM
 **/
@Slf4j
@Service
public class CorrectCourseWorkReturnServiceImpl implements CorrectCourseWorkReturnService {


    @Autowired
    private NoticeLandingManager noticeLandingManager;

    @Autowired
    private CustomAliasCastStrategyTemplate customCastStrategyTemplate;

    @Autowired
    private NotificationHandler notificationHandler;

    /**
     * 申论课后作业消息通知
     *
     * @param pushInfo
     * @throws BizException
     */
    @Override
    public void send(CorrectCourseWorkPushInfo pushInfo) throws BizException {
        List<NoticeReq> noticeReqList = Lists.newArrayList();
        CorrectReturnParams.Builder builder = CorrectFactory.correctReturnParams(correctReturnInfo);

        List<NoticeReq.NoticeUserRelation> noticeUserRelations = CorrectFactory.correctReturnUserRelations(correctReturnInfo);
        CorrectFactory.correctReturnNoticeForPush(builder, noticeUserRelations, correctReturnInfo, noticeReqList);

        List<UmengNotification> list = CorrectFactory.customCastNotifications(correctReturnInfo.getBizId(), noticeReqList, JumpTargetEnum.CORRECT_LIST);
        noticeLandingManager.insertBatch(noticeReqList);
        customCastStrategyTemplate.setNotificationList(list);
        notificationHandler.setDetailType(NoticeTypeEnum.CORRECT_FEEDBACK);
        notificationHandler.setBizId(correctReturnInfo.getBizId());
        notificationHandler.setConcurrent(true);
        notificationHandler.setPushStrategy(customCastStrategyTemplate);
        /**
         * 申论批改 - 被退回消息发送
         */
        log.info("申论人工批改被退回:{}", JSONObject.toJSONString(noticeReqList));
        notificationHandler.push();
    }
}
