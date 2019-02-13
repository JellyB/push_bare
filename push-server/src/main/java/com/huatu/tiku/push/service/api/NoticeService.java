package com.huatu.tiku.push.service.api;

import com.github.pagehelper.PageInfo;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.request.NoticeRelationReq;
import com.huatu.tiku.push.request.NoticeReq;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-06 下午7:14
 **/

public interface NoticeService {

    /**
     * 分页查询用户的消息
     * @param userId
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    PageInfo selectUserNotice(long userId, int page, int size) throws BizException;

    /**
     * 保存消息列表
     * @param req
     * @return
     * @throws BizException
     */
    Object saveNotices(NoticeReq req) throws BizException;

    /**
     * 添加user notice关系
     * @param noticeRelationReq
     * @return
     * @throws BizException
     */
    Object addUsers(NoticeRelationReq noticeRelationReq)throws BizException;

    /**
     * 消息已读
     * @param noticeId
     * @return
     * @throws BizException
     */
    Object hasRead(long noticeId) throws BizException;

    /**
     * 消息已读
     * @param userId
     * @param noticeId
     * @return
     * @throws BizException
     */
    Object hasRead(long userId, long noticeId) throws BizException;


    /**
     * 获取我的消息未读数
     * @param userId
     * @return
     * @throws BizException
     */
    int unReadNum(long userId)throws BizException;

    /**
     * 获取用户指定type类型的消息列表
     * @param userId
     * @param type
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    PageInfo noticeList4Pc(long userId, String type, int page, int size)throws BizException;

    /**
     * 逻辑删除noticeID
     * @param userId
     * @param noticeId
     * @return
     * @throws BizException
     */
    Object deleteNotice(long userId, long noticeId) throws BizException;

    /**
     * 数据刷新接口
     * @param secret
     * @param type
     * @param detailType
     * @return
     * @throws BizException
     */
    Object refresh(String secret, String type, String detailType) throws BizException;


    /**
     * 根据uName获取未读消息数
     * @param nName 用户昵称
     * @return
     * @throws BizException
     */
    Object unreadCountForPhp(String nName)throws BizException;

    /**
     * 左滑消息逻辑删除操作
     * @param userId
     * @param noticeId
     * @return
     * @throws BizException
     */
    Object deleteByLogic(long userId, long noticeId) throws BizException;


    /**
     * 全部已读
     * @param userId
     * @return
     * @throws BizException
     */
    Object readAll(long userId) throws BizException;
}
