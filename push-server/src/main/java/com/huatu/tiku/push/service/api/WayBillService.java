package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.request.WayBillReq;
import org.springframework.scheduling.annotation.Async;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-20 下午5:38
 **/


public interface WayBillService {

    /**
     * 新的运单编号信息
     * @param req
     * @return
     * @throws BizException
     */
    void info(WayBillReq.Model req) throws BizException;
}
