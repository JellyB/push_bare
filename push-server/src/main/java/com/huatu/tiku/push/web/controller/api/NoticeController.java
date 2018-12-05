package com.huatu.tiku.push.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.request.NoticeRelationReq;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.service.api.NoticeService;
import com.huatu.tiku.push.util.PageUtil;
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
@Slf4j
public class NoticeController {


    @Autowired
    private NoticeService noticeService;


    /**
     * 获取我的消息列表数据
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "{userId}")
    public Object list(@PathVariable(value = "userId") int userId,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "20") int size){

        PageInfo pageInfo = noticeService.selectUserNotice(userId, page, size);
        return PageUtil.parsePageInfo(pageInfo);
    }

    /**
     * 获取我的消息未读数
     * @param userId
     * @return
     */
    @GetMapping(value = "unRead/{userId}")
    public Object list(@PathVariable(value = "userId") int userId){

        return noticeService.unReadNum(userId);
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


    @PutMapping("read/{noticeId}")
    public Object read(@PathVariable long noticeId) throws BizException{
        return noticeService.hasRead(noticeId);
    }

}
