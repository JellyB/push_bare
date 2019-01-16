package com.huatu.tiku.push.service.api;


import com.huatu.tiku.push.constant.CourseQueueEntity;
import com.huatu.tiku.push.constant.SimpleUserWithBizId;
import com.huatu.tiku.push.constant.UserResponse;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 上午10:42
 **/
public interface UserInfoComponent {

    long SUCCESS_FLAG_USER = 1000000;

    long SUCCESS_FLAG_PHP = 10000;
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


    /**
     * 获取用户id response
     * @param userNames
     * @return
     */
    UserResponse getUserIdResponse(List<String> userNames);
}
