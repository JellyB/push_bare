package com.huatu.tiku.push.service.api.strategy;

import com.github.pagehelper.PageInfo;
import com.huatu.tiku.push.entity.NoticeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-27 上午11:04
 **/

@Component
@Slf4j
public class NoticeRespHandler {

    private AbstractNoticeResp abstractNoticeResp;

    public void setAbstractNoticeResp(AbstractNoticeResp abstractNoticeResp) {
        this.abstractNoticeResp = abstractNoticeResp;
    }

    public PageInfo build(PageInfo pageInfo, Map<Long, NoticeEntity> maps){
        return abstractNoticeResp.build(pageInfo, maps);
    }
}
