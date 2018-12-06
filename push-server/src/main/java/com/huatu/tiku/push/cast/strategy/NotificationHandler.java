package com.huatu.tiku.push.cast.strategy;

import com.huatu.tiku.push.enums.NoticeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-20 下午8:37
 **/
@Slf4j
@Component
public class NotificationHandler {


    private PushStrategy pushStrategy;

    private long bizId;

    private NoticeTypeEnum detailType;

    public long getBizId() {
        return bizId;
    }

    public void setBizId(long bizId) {
        this.bizId = bizId;
    }

    public NoticeTypeEnum getDetailType() {
        return detailType;
    }

    public void setDetailType(NoticeTypeEnum detailType) {
        this.detailType = detailType;
    }

    public void setPushStrategy(PushStrategy pushStrategy) {
        this.pushStrategy = pushStrategy;
    }


    public void push(){
        pushStrategy.push(getDetailType(), getBizId());
    }
}
