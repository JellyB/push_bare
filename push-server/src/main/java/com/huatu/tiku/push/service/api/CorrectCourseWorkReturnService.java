package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.CorrectCourseWorkPushInfo;

/**
 * 描述：申论课后作业 return 处理逻辑
 *
 * @author biguodong
 * Create time 2019-09-20 2:02 PM
 **/
public interface CorrectCourseWorkReturnService {

    /**
     * 申论课后作业消息通知
     * @param pushInfo
     * @throws BizException
     */
    void send(CorrectCourseWorkPushInfo pushInfo) throws BizException;
}
