package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import com.huatu.tiku.push.enums.NoticeReadEnum;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import com.huatu.tiku.push.manager.NoticeLandingManager;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.service.api.NoticeStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-20 上午10:42
 **/

@Slf4j
@Service
public class NoticeStoreServiceImpl implements NoticeStoreService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private NoticeLandingManager noticeLandingManager;

    /**
     * 存储notice req list
     *
     * @param noticeInsertList
     * @throws BizException
     */
    @Override
    public void store(List<NoticeReq> noticeInsertList) throws BizException {
        if(noticeInsertList.size() < RabbitMqKey.NOTICE_STORE_THRESHOLD
                && noticeInsertList.get(0).getUsers().size() < RabbitMqKey.NOTICE_STORE_THRESHOLD){
            noticeLandingManager.insertBatch(noticeInsertList);
        }else{
            NoticeReq noticeReq = new NoticeReq();
            BeanUtils.copyProperties(noticeInsertList.get(0), noticeReq, "users");
            final long noticeId = noticeLandingManager.insertNoticeEntity(noticeReq);

            noticeInsertList.get(0).getUsers().forEach(item -> {
                NoticeUserRelation noticeUserRelation = NoticeUserRelation.builder()
                        .noticeId(noticeId)
                        .userId(item.getUserId())
                        .type(noticeReq.getType())
                        .detailType(noticeReq.getDetailType())
                        .createTime(new Timestamp(System.currentTimeMillis()))
                        .updateTime(new Timestamp(System.currentTimeMillis()))
                        .status(NoticeStatusEnum.NORMAL.getValue())
                        .isRead(NoticeReadEnum.UN_READ.getValue())
                        .build();
                String message = JSONObject.toJSONString(noticeUserRelation);
                rabbitTemplate.convertAndSend(RabbitMqKey.NOTICE_PUSH_LANDING, message);
            });
        }
    }
}
