package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.CorrectReturnInfo;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 下午6:13
 **/
public interface CorrectReturnService {

    /**
     * 处理批改被退回消息
     * @param correctReturnInfo
     * @throws BizException
     */
    void sendCorrectNotice(CorrectReturnInfo correctReturnInfo) throws BizException;
}
