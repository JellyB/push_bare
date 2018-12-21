package com.huatu.tiku.push.spring.conf;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.huatu.tiku.push.request.BaseModel;
import com.huatu.tiku.push.service.api.BaseModelService;
import com.huatu.tiku.push.util.NoticeQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-21 下午2:37
 **/
@Component
@Slf4j
public class ConsumerNoticeQueue{

    static final int FIX_NUM = 2;
    @Autowired
    private BaseModelService baseModelService;

    @PostConstruct
    public void longPolling(){
        ThreadFactory threadFactory =  new  ThreadFactoryBuilder().setNameFormat("notice-cs-%s").build();
        ExecutorService executorService =new ThreadPoolExecutor(FIX_NUM, FIX_NUM,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), threadFactory, new ThreadPoolExecutor.AbortPolicy());

        executorService.submit(new PollNotice(baseModelService));
        executorService.submit(new PollNotice(baseModelService));
    }

    class PollNotice implements Runnable{

        BaseModelService baseModelService;

        public PollNotice(BaseModelService baseModelService) {
            this.baseModelService = baseModelService;
        }

        @Override
        public void run() {
            while (true) {
                try{
                    BaseModel baseModel = NoticeQueue.instance().consume();
                    baseModelService.info(baseModel);
                    log.error("baseModel:{}", JSONObject.toJSONString(baseModel));
                    log.info("queue size:{}", NoticeQueue.instance().size());
                }catch (InterruptedException e){
                    log.error("consumer notice caught an exception");
                }
            }
        }
    }
}
