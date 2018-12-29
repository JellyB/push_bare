package com.huatu.tiku.push.service.api.strategy;

import com.huatu.tiku.push.constant.BaseMsg;
import com.huatu.tiku.push.entity.NoticeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-27 上午10:40
 **/

@Service(value = "noticeRespAppStrategy")
@Slf4j
public class NoticeRespAppStrategy extends AbstractNoticeResp {

    /**
     * 组装pageinfo
     *
     * @param noticeEntity
     * @param baseMsg
     * @return
     */
    @Override
    protected void filterBaseMsg(BaseMsg baseMsg, NoticeEntity noticeEntity) {
        baseMsg.setTitle(noticeEntity.getTitle());
        baseMsg.setText(noticeEntity.getText());
    }
}
