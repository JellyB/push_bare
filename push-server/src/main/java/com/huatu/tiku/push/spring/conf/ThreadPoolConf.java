package com.huatu.tiku.push.spring.conf;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.huatu.tiku.push.constant.RabbitMqKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.util.concurrent.*;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-18 上午11:22
 **/
@Configuration
@Component
@Slf4j
public class ThreadPoolConf {


    @Bean
    public ThreadFactory threadFactory(){
        return new ThreadFactoryBuilder().setNameFormat("push-pool-%s").build();
    }

    @Bean(value = "pushExecutorService")
    public ExecutorService executorService(){
        ExecutorService pool = new ThreadPoolExecutor(5,
                NumberUtils.parseNumber(String.valueOf(RabbitMqKey.PUSH_STRATEGY_THRESHOLD), Integer.class),
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(1024),
                threadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }
}
