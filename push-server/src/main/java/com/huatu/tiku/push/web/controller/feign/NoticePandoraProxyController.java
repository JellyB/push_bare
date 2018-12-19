package com.huatu.tiku.push.web.controller.feign;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huatu.common.exception.BizException;
import com.huatu.springboot.web.version.mapping.annotation.ApiVersion;
import com.huatu.tiku.push.service.feign.NoticePandoraProxy;
import com.huatu.tiku.push.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

        log.info("noticeList.request params.userId:{}, page:{}, size:{}", userId, page, size);
        PageInfo pageInfo = noticePandoraProxy.selectUserNotice(userId, page, size);
        log.info("page info:{}", JSONObject.toJSONString(PageUtil.parsePageInfo(pageInfo)));
        return PageUtil.parsePageInfo(pageInfo);
    }

    /**
     * 获取我的消息未读数
     * @param userId
     * @return
     */
    @GetMapping(value = "unReadCount")
    public Object list(@RequestParam(value = "userId") int userId){
        log.info("unReadCount.request params.userId:{}", userId);
        return noticePandoraProxy.unReadNum(userId);
    }

    /**
     * 我的消息已读
     * @param noticeId
     * @return
     * @throws BizException
     */
    @PutMapping("hasRead")
    public Object read(@RequestParam long noticeId) throws BizException{
        log.info("hasRead.request params.noticeId:{}", noticeId);
        return noticePandoraProxy.hasRead(noticeId);
    }

}
