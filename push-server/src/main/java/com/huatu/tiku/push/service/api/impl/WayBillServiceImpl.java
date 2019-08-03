package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.UmengNotification;
import com.huatu.tiku.push.cast.strategy.CustomAliasCastStrategyTemplate;
import com.huatu.tiku.push.cast.strategy.NotificationHandler;
import com.huatu.tiku.push.constant.AbstractBuilder;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.constant.UserResponse;
import com.huatu.tiku.push.enums.JumpTargetEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.manager.NoticeLandingManager;
import com.huatu.tiku.push.quartz.factory.FeedBackCastFactory;
import com.huatu.tiku.push.quartz.factory.WayBillFactory;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.request.WayBillReq;
import com.huatu.tiku.push.service.api.UserInfoComponent;
import com.huatu.tiku.push.service.api.WayBillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-20 下午5:39
 **/

@Service
@Slf4j
public class WayBillServiceImpl implements WayBillService {


    @Autowired
    private CustomAliasCastStrategyTemplate customCastStrategyTemplate;

    @Autowired
    private NoticeLandingManager noticeLandingManager;

    @Autowired
    private NotificationHandler notificationHandler;

    @Autowired
    private UserInfoComponent userInfoComponent;

    /**
     * 新的运单编号信息
     *
     * @param req
     * @return
     * @throws BizException
     */
    @Override
    public void info(WayBillReq.Model req) throws BizException {
        List<NoticeReq> noticeReqList = Lists.newArrayList();
        List<String> userNames = Lists.newArrayList();
        AbstractBuilder builder = WayBillFactory.builder(req);
        userNames.add(req.getUserName());
        UserResponse userResponse = userInfoComponent.getUserIdResponse(userNames);
        long userId = 0;
        if(Long.valueOf(userResponse.getCode()) == UserInfoComponent.SUCCESS_FLAG_USER && CollectionUtils.isNotEmpty(userResponse.getData())){
            userId = userResponse.getData().get(0).getUserId();
        }
        if(userId == 0){
            throw new BizException(NoticePushErrors.NOTICE_USER_LIST_EMPTY);
        }
        List<NoticeReq.NoticeUserRelation> noticeUserRelations = WayBillFactory.wayBillNoticeRelation(userId);
        WayBillFactory.noticeForPush(builder, noticeUserRelations, req, noticeReqList);

        List<UmengNotification> list = FeedBackCastFactory.customCastNotifications(0L, noticeReqList, JumpTargetEnum.NOTICE_CENTER);
        noticeLandingManager.insertBatch(noticeReqList);
        customCastStrategyTemplate.setNotificationList(list);
        notificationHandler.setDetailType(builder.getNoticeTypeEnum());
        notificationHandler.setBizId(0L);
        notificationHandler.setConcurrent(false);
        notificationHandler.setPushStrategy(customCastStrategyTemplate);
        /**
         * 发送
         */
        log.info("push waybill info:{}", JSONObject.toJSONString(noticeReqList));
        notificationHandler.push();
    }
}
