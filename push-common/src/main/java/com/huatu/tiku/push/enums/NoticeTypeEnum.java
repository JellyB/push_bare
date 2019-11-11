package com.huatu.tiku.push.enums;

import com.huatu.tiku.push.constant.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-07 下午1:24
 **/

@AllArgsConstructor
@Getter
public enum NoticeTypeEnum{
    REMOTE_NOTICE("通知远程服务", "调度远程服务", NoticeParentTypeEnum.REMOTE, RemoteParams.DETAIL_TYPE),
    COURSE_REMIND("%s 您有一节直播课，不要迟到哦~", "主讲老师:  %s", NoticeParentTypeEnum.COURSE, CourseParams.REMIND),
    COURSE_READY("您的直播课马上开始，立即进入！", "",  NoticeParentTypeEnum.COURSE, CourseParams.READY),
    MOCK_ONLINE("%s报名开始啦~", "", NoticeParentTypeEnum.MOCK, MockParams.ON_LINE),
    MOCK_REMIND("%马上开考！", "", NoticeParentTypeEnum.MOCK, MockParams.REMIND),
    MOCK_REPORT("%报告出炉", "", NoticeParentTypeEnum.MOCK, MockParams.REPORT),
    ORDER_SEND("发货提醒", "", NoticeParentTypeEnum.ORDER, WayBillSendParams.DETAIL_TYPE),
    ORDER_SIGN("订单已签收", "", NoticeParentTypeEnum.ORDER, WayBillSignParams.DETAIL_TYPE),
    CORRECT_FEEDBACK("您的纠错有反馈啦~", "", NoticeParentTypeEnum.FEEDBACK, FeedBackCorrectParams.DETAIL_TYPE),
    SUGGEST_FEEDBACK("您的建议有反馈啦~", "您于 %s 日提交的意见反馈收到了回复：%s", NoticeParentTypeEnum.FEEDBACK, FeedBackSuggestParams.DETAIL_TYPE),
    CORRECT_RETURN("您有申论人工批改申请被退回，火速查看~", "您于 %s 提交的申论人工批改申请因 “%s” 被驳回。", NoticeParentTypeEnum.CORRECT, CorrectReturnParams.DETAIL_TYPE),
    CORRECT_REPORT("您的申论人工批改出报告了，快来看看吧~", "您于 %s 提交的申论%s人工批改申请报告已出。", NoticeParentTypeEnum.CORRECT, CorrectReportParams.DETAIL_TYPE),
    CORRECT_RETURN_COURSE_WORK("您的申论人工批改课后作业被退回，火速查看~", "您于%s提交的“%s”申论人工批改课后作业因“%s”被驳回。", NoticeParentTypeEnum.CORRECT_COURSE_WORK, CorrectCourseWorkReturnParams.DETAIL_TYPE),
    CORRECT_REPORT_COURSE_WORK("您的申论人工批改课后作业出报告了，快去看看吧~", "您于%s提交的“%s”申论人工批改课后作业报告已出。", NoticeParentTypeEnum.CORRECT_COURSE_WORK, CorrectCourseWorkReportParams.DETAIL_TYPE);

    private String title;
    private String text4Data;

    private NoticeParentTypeEnum type;

    private String detailType;

    public static NoticeTypeEnum create(String type, String detailType){
        NoticeParentTypeEnum parentTypeEnum = NoticeParentTypeEnum.create(type);
        for(NoticeTypeEnum noticeTypeEnum : values()){
            if(type.equals(parentTypeEnum.getType()) && detailType.equals(noticeTypeEnum.getDetailType())){
                return noticeTypeEnum;
            }
        }
        return null;
    }

    public NoticeViewEnum getViewEnum(){
        return this.getType().getParent();
    }
}