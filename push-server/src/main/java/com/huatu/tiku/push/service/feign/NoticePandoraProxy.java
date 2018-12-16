package com.huatu.tiku.push.service.feign;

import com.github.pagehelper.PageInfo;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.request.NoticeRelationReq;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.response.NoticeResp;
import com.huatu.tiku.push.service.api.NoticeService;
import com.huatu.tiku.push.util.SplitParamsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-06 下午1:31
 **/
@Service(value = "noticePandoraProxy")
@Slf4j
public class NoticePandoraProxy implements NoticeService{

    @Autowired
    @Qualifier(value = "noticeService")
    private NoticeService noticeService;


    /**
     * 分页查询用户的消息
     * feign 调用对noticeId进行处理，包含userId 以及 noticeId
     * 以便定位分表
     * @param userId
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    @Override
    public PageInfo selectUserNotice(long userId, int page, int size) throws BizException {
        PageInfo pageInfo =  noticeService.selectUserNotice(userId, page, size);
        List<NoticeResp> list = pageInfo.getList();
        list.forEach(noticeResp -> {
            long noticeId = SplitParamsUtil.buildComplexNoticeId(noticeResp.getUserId(), noticeResp.getNoticeId());
            noticeResp.setNoticeId(noticeId);
        });
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * 保存消息列表
     *
     * @param req
     * @return
     * @throws BizException
     */
    @Override
    public Object saveNotices(NoticeReq req) throws BizException {
        return noticeService.saveNotices(req);
    }

    /**
     * 添加user notice关系
     *
     * @param noticeRelationReq
     * @return
     * @throws BizException
     */
    @Override
    public Object addUsers(NoticeRelationReq noticeRelationReq) throws BizException {
        return noticeService.addUsers(noticeRelationReq);
    }

    /**
     * 消息已读
     *
     * @param noticeId
     * @return
     * @throws BizException
     */
    @Override
    public Object hasRead(long noticeId) throws BizException {
        long[] params = SplitParamsUtil.obtainSplitArray(noticeId);
        return hasRead(params[0], params[1]);
    }

    /**
     * 消息已读
     *
     * @param userId
     * @param noticeId
     * @return
     * @throws BizException
     */
    @Override
    public Object hasRead(long userId, long noticeId) throws BizException {
        return noticeService.hasRead(userId, noticeId);
    }

    /**
     * 获取我的消息未读数
     *
     * @param userId
     * @return
     * @throws BizException
     */
    @Override
    public int unReadNum(long userId) throws BizException {
        return noticeService.unReadNum(userId);
    }

    /**
     * 获取用户指定type类型的消息列表
     *
     * @param userId
     * @param type
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    @Override
    public Object noticeList4Pc(long userId, String type, int page, int size) throws BizException {
        return null;
    }
}
