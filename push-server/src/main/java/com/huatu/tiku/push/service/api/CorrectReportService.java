package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.CorrectReportInfo;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 下午6:13
 **/
public interface CorrectReportService {

    /**
     * 处理批改查看报告消息
     * @param correctReportInfo
     * @throws BizException
     */
    void sendCorrectNotice(CorrectReportInfo correctReportInfo) throws BizException;
}
