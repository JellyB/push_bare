package com.huatu.tiku.push.web.controller.api;

import com.huatu.tiku.common.bean.user.UserSession;
import com.huatu.tiku.push.service.api.NoticeService;
import com.huatu.tiku.springboot.users.support.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-14 下午6:29
 **/
@RestController
@RequestMapping(value = "pc")
@Slf4j
public class NoticePcController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping(value = "type/{type}")
    public Object obtainNoticeByType(@Token UserSession userSession,
                                     @PathVariable(value = "type")String type,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "size", defaultValue = "20") int size){
        long userId = userSession.getId();
        return noticeService.noticeList4Pc(userId, type, page, size);

    }
}
