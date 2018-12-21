package com.huatu.tiku.push.quartz.factory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.*;
import com.huatu.tiku.push.enums.DisplayTypeEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.request.WayBillReq;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cms.PasswordRecipientId;

import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-20 下午6:15
 **/
public class WayBillFactory extends AbstractFactory {


    private static final int SEND = 0;

    private static final int SIGN = 1;


    /**
     * builder 入口
     * @param req
     * @return
     */
    public static AbstractBuilder builder(WayBillReq.Model req){
        if(req.getType() == SEND){
            return wayBillSendParams(req);
        }
        if(req.getType() == SIGN){
            return wayBillSignParams(req);
        }
        return null;
    }
    /**
     * 订单签收params
     * @param req
     * @return
     */
    public static WayBillSignParams.Builder wayBillSignParams(WayBillReq.Model req){
        WayBillSignParams.Builder builder = WayBillSignParams.Builder.builder()
                .orderId(req.getWayBillNo())
                .orderTime(req.getCreateTime())
                .build();
        return builder;
    }

    /**
     * 订单发出params
     * @param req
     * @return
     */
    public static WayBillSendParams.Builder wayBillSendParams(WayBillReq.Model req){
        WayBillSendParams.Builder builder = WayBillSendParams.Builder.builder()
                .orderId(req.getWayBillNo())
                .orderTime(req.getCreateTime())
                .build();
        return builder;
    }



    /**
     * 推送用户信息--订单
     * @param userId
     * @return
     */
    public static List<NoticeReq.NoticeUserRelation> wayBillNoticeRelation(long userId){
        List<NoticeReq.NoticeUserRelation> list = Lists.newArrayList();
        list.add(NoticeReq.NoticeUserRelation
                .builder()
                .noticeId(0L)
                .userId(userId)
                .createTime(new Date())
                .build());
        return list;
    }


    /**
     * 推送消息--建议
     * @param builder
     * @param noticeUserRelations
     * @param req
     * @param noticeReqList
     */
    public static void noticeForPush(AbstractBuilder builder, List<NoticeReq.NoticeUserRelation> noticeUserRelations,
                                            WayBillReq.Model req, List<NoticeReq> noticeReqList ){

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
}
