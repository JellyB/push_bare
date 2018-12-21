package com.huatu.tiku.push.util;

import com.huatu.tiku.push.request.BaseModel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-21 下午3:34
 **/
@Slf4j
public final class NoticeQueue {

    private static final int MAX_NOTICE_SIZE = 100;

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
    public void produce(BaseModel baseModel) throws InterruptedException{
        queue.put(baseModel);
        log.info("<><><><><><><><><><><><><><><><><><><><><><><><><><>, {}", queue.size() );
    }

    /**
     * 消费消息
     * @return
     * @throws InterruptedException
     */
    public BaseModel consume() throws InterruptedException{
        return queue.take();
    }

    public int size(){
        return queue.size();
    }
}
