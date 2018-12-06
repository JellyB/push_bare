package com.huatu.tiku.push.cast.strategy;

import com.huatu.tiku.push.cast.AndroidCustomCast;
import com.huatu.tiku.push.cast.IosCustomCast;
import com.huatu.tiku.push.enums.NoticeTypeEnum;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-20 下午8:24
 **/
public interface PushStrategy {

    String SUCCESS = "SUCCESS";

    String FAIL = "FAIL";

    String TASK_ID = "task_id";

    String MSG_ID = "msg_id";

    String ANDROID = AndroidCustomCast.class.getSimpleName();

    String IOS = IosCustomCast.class.getSimpleName();

    /**
     * 推送接口
     * @param bizId
     * @param noticeTypeEnum
     */
    void push(NoticeTypeEnum noticeTypeEnum, long bizId);
}
