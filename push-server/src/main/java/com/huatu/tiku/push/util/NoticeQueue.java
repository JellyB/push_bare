package com.huatu.tiku.push.util;

import com.huatu.tiku.push.request.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-21 下午3:34
 **/
@Slf4j
public class NoticeQueue {

    private static final int MAX_NOTICE_SIZE = 1024;

    private static final LinkedBlockingDeque<BaseModel> queue = new LinkedBlockingDeque<>(MAX_NOTICE_SIZE);

    private NoticeQueue() {
    }

    private static class SingletonHolder{
        private static  NoticeQueue noticeQueue = new NoticeQueue();
    }

    /**
     * 单例队列
     * @return
     */
    public static NoticeQueue instance(){
        return SingletonHolder.noticeQueue;
    }

    /**
     * 生产消息
     * @param baseModel
     * @throws InterruptedException
     */
    @Async
    public void produce(BaseModel baseModel) throws InterruptedException{
        queue.put(baseModel);
        log.debug("queue size, {}", queue.size() );
    }

    /**
     * 消费消息
     * @return
     * @throws InterruptedException
     */
    public BaseModel consume() throws InterruptedException{
        return queue.poll(100, TimeUnit.SECONDS);
    }

    public int size(){
        return queue.size();
    }
}
