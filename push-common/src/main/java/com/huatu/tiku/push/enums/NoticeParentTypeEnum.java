package com.huatu.tiku.push.enums;

import com.huatu.tiku.push.constant.CourseParams;
import com.huatu.tiku.push.constant.FeedBackParams;
import com.huatu.tiku.push.constant.MockParams;
import com.huatu.tiku.push.constant.OrderParams;
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

    COURSE(CourseParams.TYPE), MOCK(MockParams.TYPE), ORDER(OrderParams.TYPE), FEEDBACK(FeedBackParams.TYPE);

    private String type;
}
