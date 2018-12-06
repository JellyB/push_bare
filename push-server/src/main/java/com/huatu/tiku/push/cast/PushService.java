package com.huatu.tiku.push.cast;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.request.NoticeReq;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 下午2:55
 **/
@Deprecated
public interface PushService<T> {



    /**
     * 消息推送
     * @param noticeReqList
     * @return
     */
    void push(List<NoticeReq> noticeReqList);


    /**
     * 消息推送入口
     * @param t
     * @throws BizException
     */
    void push(T t)throws BizException;


    /**
     * 终端上传file_id
     * @param classId
     * @return
     * @throws BizException
     */
    FileUploadTerminal uploadTerminal(long classId)throws BizException;

}
