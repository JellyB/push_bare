package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.request.NoticeReq;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-20 上午10:41
 **/
public interface NoticeStoreService {


    /**
     * 存储notice req list
     * @param noticeInsertList
     * @throws BizException
     */
    void store(List<NoticeReq> noticeInsertList)throws BizException;
}
