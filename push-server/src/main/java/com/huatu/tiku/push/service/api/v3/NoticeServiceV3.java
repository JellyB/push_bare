package com.huatu.tiku.push.service.api.v3;

import com.github.pagehelper.PageInfo;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import org.springframework.scheduling.annotation.Async;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-26 下午6:48
 **/
public interface NoticeServiceV3 {
    /**
     * 我的消息全部已读
     * @param userId
     * @return
     * @throws BizException
     */
    Object readAll(long userId) throws BizException;

    /**
     * 我的视图列表
     * @param userId
     * @return
     * @throws BizException
     */
    Object viewList(long userId) throws BizException;

    /**
     * 临时隐藏当前 view
     * @param userId
     * @param view
     * @return
     * @throws BizException
     */
    @Deprecated
    Object hideView(long userId, String view) throws BizException;

    /**
     * 具体 view 数据列表
     * @param userId
     * @param type
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    PageInfo typeViewList(long userId, String type, int page, int size) throws BizException;

    /**
     * 永久删除一条消息
     * @param userId
     * @param noticeId
     * @return
     * @throws BizException
     */
    Object deleteNoticeById(long userId, long noticeId)throws BizException;

}


