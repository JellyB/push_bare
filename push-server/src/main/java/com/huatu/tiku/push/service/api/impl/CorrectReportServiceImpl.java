package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.UmengNotification;
import com.huatu.tiku.push.cast.strategy.CustomAliasCastStrategyTemplate;
import com.huatu.tiku.push.cast.strategy.NotificationHandler;
import com.huatu.tiku.push.constant.*;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.manager.NoticeLandingManager;
import com.huatu.tiku.push.quartz.factory.CorrectFactory;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.service.api.CorrectReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CorrectReportServiceImpl implements CorrectReportService {


    @Autowired
    private NoticeLandingManager noticeLandingManager;

    @Autowired
    private CustomAliasCastStrategyTemplate customCastStrategyTemplate;

    @Autowired
    private NotificationHandler notificationHandler;

    /**
     * 处理申论批改查看报告
     *
     * @param correctReportInfo
     * @throws BizException
     */
    @Override
    public void sendCorrectNotice(CorrectReportInfo correctReportInfo) throws BizException {
        List<NoticeReq> noticeReqList = Lists.newArrayList();
        CorrectReportParams.Builder builder = CorrectFactory.correctReportParams(correctReportInfo);

        List<NoticeReq.NoticeUserRelation> noticeUserRelations = CorrectFactory.correctReportUserRelations(correctReportInfo);
        CorrectFactory.correctReportNoticeForPush(builder, noticeUserRelations, correctReportInfo, noticeReqList);

        List<UmengNotification> list = CorrectFactory.customCastNotifications(correctReportInfo.getBizId(), noticeReqList);
        noticeLandingManager.insertBatch(noticeReqList);
        customCastStrategyTemplate.setNotificationList(list);
        notificationHandler.setDetailType(NoticeTypeEnum.CORRECT_FEEDBACK);
        notificationHandler.setBizId(correctReportInfo.getBizId());
        notificationHandler.setConcurrent(true);
        notificationHandler.setPushStrategy(customCastStrategyTemplate);
        /**
         * 申论人工批改 - 报告出炉 - 消息发送
         */
        log.info("申论人工批改报告出炉消息发送:{}", JSONObject.toJSONString(noticeReqList));
        notificationHandler.push();
    }
}
