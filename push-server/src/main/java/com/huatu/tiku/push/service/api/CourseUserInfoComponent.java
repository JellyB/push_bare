package com.huatu.tiku.push.service.api;


import com.huatu.tiku.push.constant.CourseQueueEntity;
import com.huatu.tiku.push.constant.SimpleUserWithBizId;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 上午10:42
 **/
public interface CourseUserInfoComponent {
    /**
     * 抓取用户 uname
     * @param courseQueueEntity
     */
    void fetchUserName(CourseQueueEntity courseQueueEntity);

    /**
     * 处理username list
     * @param simpleUserWithBizId
     */
    void fetchUserId(SimpleUserWithBizId simpleUserWithBizId);
}
