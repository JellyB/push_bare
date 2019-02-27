package com.huatu.tiku.push.web.controller.api.v3;

import com.github.pagehelper.PageInfo;
import com.huatu.common.exception.BizException;
import com.huatu.springboot.web.version.mapping.annotation.ApiVersion;
import com.huatu.tiku.common.bean.user.UserSession;
import com.huatu.tiku.push.service.api.v3.NoticeServiceV3;
import com.huatu.tiku.push.util.PageUtil;
import com.huatu.tiku.springboot.users.support.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-26 下午6:30
 **/

@RestController
@RequestMapping(value = "notice")
@ApiVersion("v3")
@Slf4j
public class NoticeControllerV3 {


    @Autowired
    private NoticeServiceV3 noticeService;

    /**
     * 消息全部已读
     * @param userSession
     * @return
     * @throws BizException
     */
    @PutMapping("readAll")
    public Object readAll(@Token UserSession userSession,
                          @RequestHeader(value = "terminal") int terminal,
                          @RequestHeader(value = "cv") String cv) throws BizException{


        long userId = userSession.getId();
        return noticeService.readAll(userId);
    }

    /**
     * 我的notice view
     * @param userSession
     * @return
     */
    @GetMapping("/view")
    public Object viewList(@Token UserSession userSession,
                           @RequestHeader(value = "terminal") int terminal,
                           @RequestHeader(value = "cv") String cv){
       long userId = userSession.getId();
       return noticeService.viewList(userId);
    }

    /**
     * 隐藏 view
     * @param userSession
     * @param type
     * @return
     */
    @DeleteMapping(value = "/view/{type}")
    public Object hideView(@Token UserSession userSession,
                           @RequestHeader(value = "terminal") int terminal,
                           @RequestHeader(value = "cv") String cv,
                           @PathVariable(value = "type") String type){
        long userId = userSession.getId();
        return noticeService.hideView(userId, type);
    }

    /**
     * 获取我的消息列表数据
     * @param userSession
     * @param view
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/{view}/noticeList")
    public Object typeList(@Token UserSession userSession,
                           @RequestHeader(value = "terminal") int terminal,
                           @RequestHeader(value = "cv") String cv,
                           @PathVariable(value = "view") String view,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "20") int size){
        long userId = userSession.getId();
        PageInfo pageInfo = noticeService.typeViewList(userId, view, page, size);
        return PageUtil.parsePageInfo(pageInfo);
    }


    /**
     * 左滑逻辑删除单条消息
     * @param noticeId
     * @return
     * @throws BizException
     */
    @DeleteMapping("/{noticeId}")
    public Object deleteByLogic(@Token UserSession userSession,
                                @PathVariable(value = "noticeId") long noticeId) throws BizException{

        long userId = userSession.getId();
        return noticeService.deleteNoticeById(userId, noticeId);
    }
}
