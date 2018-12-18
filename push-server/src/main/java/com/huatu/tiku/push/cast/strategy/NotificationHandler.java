package com.huatu.tiku.push.cast.strategy;

import com.huatu.tiku.push.enums.NoticeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-20 下午8:37
 **/
@Slf4j
@Component
public class NotificationHandler {


    @Autowired
    private ExecutorService executorService;

    private PushStrategy pushStrategy;

    private long bizId;

    private boolean isConcurrent;

    public boolean isConcurrent() {
        return isConcurrent;
    }

    public void setConcurrent(boolean concurrent) {
        isConcurrent = concurrent;
    }

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
        if(isConcurrent()){
            executorService.execute(()-> pushStrategy.push(getDetailType(), getBizId()));
        }else{
            pushStrategy.push(getDetailType(), getBizId());
        }
    }
}
