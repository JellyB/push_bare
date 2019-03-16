package com.huatu.tiku.push.spring.conf;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

import static com.huatu.common.consts.ApolloConfigConsts.NAMESPACE_HT_REDIS_QUEUE;

/**
 * @author hanchao
 * @date 2017/11/1 2:07
 */
@Configuration
@EnableApolloConfig(NAMESPACE_HT_REDIS_QUEUE)
public class RedisQueueConfig {
}
