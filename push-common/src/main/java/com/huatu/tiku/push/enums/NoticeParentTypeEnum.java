package com.huatu.tiku.push.enums;

import com.huatu.tiku.push.constant.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-12 上午11:07
 **/
@AllArgsConstructor
@Getter
public enum  NoticeParentTypeEnum {

    COURSE(CourseParams.TYPE, NoticeViewEnum.COURSE),
    MOCK(MockParams.TYPE, NoticeViewEnum.PLAT_FORM),
    ORDER(WayBillParams.TYPE, NoticeViewEnum.LOGISTICS),
    FEEDBACK(FeedBackParams.TYPE, NoticeViewEnum.FEED_BACK),
    COURSE_WORK(CourseWorkParams.TYPE, NoticeViewEnum.COURSE),
    COURSE_TEST(CourseTestParams.TYPE, NoticeViewEnum.COURSE),
    SMALL_MOCK(SmallMockParams.TYPE, NoticeViewEnum.PLAT_FORM);

    private String type;

    private NoticeViewEnum parent;

    public static NoticeParentTypeEnum create(String type){
        for(NoticeParentTypeEnum noticeParentTypeEnum : values()){
            if(type.equals(noticeParentTypeEnum.getType())){
                return noticeParentTypeEnum;
            }
        }
        return null;
    }

}
