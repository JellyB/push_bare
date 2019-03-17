package com.huatu.tiku.push.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.huatu.common.exception.BizException;
import com.huatu.springboot.web.version.mapping.annotation.ApiVersion;
import com.huatu.tiku.common.bean.user.UserSession;
import com.huatu.tiku.push.request.NoticeRelationReq;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.service.api.NoticeService;
import com.huatu.tiku.push.util.PageUtil;
import com.huatu.tiku.springboot.users.support.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-06 下午4:39
 **/

@RestController
@RequestMapping(value = "notice")
@ApiVersion("v2")
@Slf4j
public class NoticeController {


    @Autowired
    private NoticeService noticeService;


    /**
     * 获取我的消息列表数据
     * @param userSession
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "noticeList")
    public Object list(@Token UserSession userSession,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "20") int size){
        long userId = userSession.getId();
        PageInfo pageInfo = noticeService.selectUserNotice(userId, page, size);
        return PageUtil.parsePageInfo(pageInfo);
    }

    /**
     * 获取我的消息未读数
     * @param userSession
     * @return
     */
    @GetMapping(value = "unReadCount")
    public Object list(@Token UserSession userSession){
        /*long userId = userSession.getId();
        return noticeService.unReadNum(userId);*/
        return 0;
    }


    /**
     * 我的消息-已读
     * @param userSession
     * @param noticeId
     * @return
     * @throws BizException
     */
    @PutMapping("hasRead")
    public Object read(@Token UserSession userSession,
                       @RequestParam long noticeId) throws BizException{

        long userId = userSession.getId();
        return noticeService.hasRead(userId, noticeId);
    }


    /**
     * 保存notice 和 user 关系
     * @param req
     * @return
     */
    @PostMapping
    public Object saveNotices(@RequestBody NoticeReq req){
        return noticeService.saveNotices(req);
    }

    /**
     * 新增关系
     * @param noticeRelationReq
     * @return
     * @throws BizException
     */
    @PostMapping("addUser")
    public Object addUsers(@RequestBody NoticeRelationReq noticeRelationReq) throws BizException{
        return noticeService.addUsers(noticeRelationReq);
    }
}
