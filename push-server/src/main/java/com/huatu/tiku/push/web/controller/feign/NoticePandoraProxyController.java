package com.huatu.tiku.push.web.controller.feign;

import com.github.pagehelper.PageInfo;
import com.huatu.common.exception.BizException;
import com.huatu.springboot.web.version.mapping.annotation.ApiVersion;
import com.huatu.tiku.push.service.feign.NoticePandoraProxy;
import com.huatu.tiku.push.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：潘多拉notice controller 代理
 *
 * @author biguodong
 * Create time 2018-12-06 下午1:21
 **/

@RestController
@RequestMapping(value = "feign")
@ApiVersion("v1")
public class NoticePandoraProxyController {

    @Autowired
    private NoticePandoraProxy noticePandoraProxy;

    /**
     * 获取我的消息列表数据
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "noticeList")
    public Object list(@RequestParam(value = "userId") int userId,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "20") int size){

        PageInfo pageInfo = noticePandoraProxy.selectUserNotice(userId, page, size);
        return PageUtil.parsePageInfo(pageInfo);
    }

    /**
     * 获取我的消息未读数
     * @param userId
     * @return
     */
    @GetMapping(value = "unReadCount")
    public Object list(@RequestParam(value = "userId") int userId){

        return noticePandoraProxy.unReadNum(userId);
    }

    /**
     * 我的消息已读
     * @param noticeId
     * @return
     * @throws BizException
     */
    @GetMapping("hasRead")
    public Object read(@RequestParam long noticeId) throws BizException{
        return noticePandoraProxy.hasRead(noticeId);
    }

}
