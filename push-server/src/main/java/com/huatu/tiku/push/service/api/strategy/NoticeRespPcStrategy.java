package com.huatu.tiku.push.service.api.strategy;

import com.huatu.tiku.push.constant.*;
import com.huatu.tiku.push.entity.NoticeEntity;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.service.api.factory.BaseMsgFactory;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-27 上午10:39
 **/
@Slf4j
@Service(value = "noticeRespPcStrategy")
public class NoticeRespPcStrategy extends AbstractNoticeResp {

    private static final String DEFAULT_COURSE_TIME = "00:00";
    private static final String DEFAULT_REPLY_CONTENT = "您的反馈已收到，感谢您的反馈。";
    /**
     * 组装 pageInfo
     *
     * @param noticeEntity
     * @return
     */
    @Override
    protected void filterBaseMsg(BaseMsg baseMsg, NoticeEntity noticeEntity) {
        try{
            NoticeTypeEnum noticeTypeEnum = NoticeTypeEnum.create(noticeEntity.getType(), noticeEntity.getDetailType());
            switch (noticeTypeEnum){
                case COURSE_REMIND:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum, dealCourseStartTime(baseMsg), dealCourseClassTitle(baseMsg));
                    break;
                case COURSE_READY:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum, dealCourseClassTitle(baseMsg));
                    break;
                case CORRECT_FEEDBACK:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum, dealCorrectSource(baseMsg), dealCorrectReply(baseMsg));
                    break;
                case SUGGEST_FEEDBACK:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum, dealSuggestCreateTime(baseMsg), dealSuggestTitle(baseMsg), dealSuggestReply(baseMsg));
                    break;
                case MOCK_ONLINE:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum);
                    break;
                case MOCK_REMIND:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum);
                    break;
                case MOCK_REPORT:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum);
                    break;
                case ORDER_SEND:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum);
                    break;
                case ORDER_SIGN:
                    BaseMsgFactory.build(baseMsg, noticeTypeEnum);
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            log.error("build baseMsg custom filed error depends on noticeTypeEnum,type:{},detailType:{}", noticeEntity.getType(), noticeEntity.getDetailType());
        }
    }

    private static String dealCourseStartTime(BaseMsg baseMsg){
        Map<String, Object> map = baseMsg.getCustom();
        Object starTimeObj = map.get(CourseParams.START_TIME);
        if(null == starTimeObj){
            return DEFAULT_COURSE_TIME;
        }
        String starTimeStr = String.valueOf(starTimeObj);
        long time = Long.valueOf(starTimeStr);
        Date date = new Date(time);
        return NoticeTimeParseUtil.simpleDateFormat.print(date.getTime());
    }

    private static String dealCourseClassTitle(BaseMsg baseMsg){
        Map<String, Object> map = baseMsg.getCustom();
        Object classTitle = map.get(CourseParams.CLASS_TITLE);
        return String.valueOf(classTitle);
    }

    private static String dealCorrectSource(BaseMsg baseMsg){
        Map<String, Object> map = baseMsg.getCustom();
        Object source = map.get(FeedBackCorrectParams.SOURCE);
        return StringUtils.trimToEmpty(String.valueOf(source == null? "" : source));
    }

    private static String dealCorrectReply(BaseMsg baseMsg){
        Map<String, Object> map = baseMsg.getCustom();
        Object reply = map.get(FeedBackCorrectParams.REPLY);
        return StringUtils.trimToEmpty(String.valueOf(reply == null? "" : reply));
    }

    private static String dealSuggestCreateTime(BaseMsg baseMsg){
        Map<String, Object> map = baseMsg.getCustom();
        Object createTime = map.get(Params.CREATE_TIME);
        return StringUtils.trimToEmpty(String.valueOf(createTime == null? "" : createTime));
    }

    private static String dealSuggestTitle(BaseMsg baseMsg){
        Map<String, Object> map = baseMsg.getCustom();
        Object title = map.get(FeedBackSuggestParams.TITLE);
        return StringUtils.trimToEmpty(String.valueOf(title == null? "" : title));
    }

    private static String dealSuggestReply(BaseMsg baseMsg){
        Map<String, Object> map = baseMsg.getCustom();
        Object reply = map.get(FeedBackSuggestParams.REPLY);
        return StringUtils.trimToEmpty(String.valueOf(reply == null? DEFAULT_REPLY_CONTENT : reply));
    }



}
