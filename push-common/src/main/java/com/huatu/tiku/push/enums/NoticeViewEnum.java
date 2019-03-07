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
    COURSE("课程通知", NoticeViewConstant.COURSE, false, new NoticeParentTypeEnum[]{
            NoticeParentTypeEnum.COURSE
    }),
    /**
     * 平台通知
     */
    PLAT_FORM("平台通知", NoticeViewConstant.PLAT_FORM, false, new NoticeParentTypeEnum[]{
            NoticeParentTypeEnum.MOCK
    }),
    /**
     * 物流通知
     */
    LOGISTICS("物流通知", NoticeViewConstant.LOGISTICS, true, new NoticeParentTypeEnum[]{
            NoticeParentTypeEnum.ORDER,
    }),

    /**
     * 反馈通知
     */
    FEED_BACK("反馈通知", NoticeViewConstant.FEED_BACK, true, new NoticeParentTypeEnum[]{
            NoticeParentTypeEnum.FEEDBACK
    });

    private String name;

    private String view;

    /**
     * 此类型的数据用户点击后直接清空未读数
     */
    private boolean readAll;

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
