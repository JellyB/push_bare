package com.huatu.tiku.push.enums;

import com.huatu.tiku.push.constant.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午9:49
 **/
@AllArgsConstructor
@Getter
public enum  NoticeViewEnum {
    /**
     * 课程通知
     */
    COURSE("课程通知", NoticeViewConstant.COURSE, new NoticeParentTypeEnum[]{
            NoticeParentTypeEnum.COURSE
    }),
    /**
     * 平台通知
     */
    PLAT_FORM("平台通知", NoticeViewConstant.PLAT_FORM, new NoticeParentTypeEnum[]{
            NoticeParentTypeEnum.MOCK
    }),
    /**
     * 物流通知
     */
    LOGISTICS("物流通知", NoticeViewConstant.LOGISTICS, new NoticeParentTypeEnum[]{
            NoticeParentTypeEnum.ORDER,
    }),

    /**
     * 反馈通知
     */
    FEED_BACK("反馈通知", NoticeViewConstant.FEED_BACK, new NoticeParentTypeEnum[]{
            NoticeParentTypeEnum.FEEDBACK
    });

    private String name;

    private String view;

    private NoticeParentTypeEnum[] child;

    public static NoticeViewEnum create(String view){
        for(NoticeViewEnum noticeViewEnum : values()){
            if(noticeViewEnum.getView().equals(view)){
                return noticeViewEnum;
            }
        }
        return null;
    }
}
