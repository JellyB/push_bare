package com.huatu.tiku.push.spring.conf;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.huatu.common.consts.ApolloConfigConsts;
import org.springframework.context.annotation.Configuration;

/**
 * @author biguodong
 */
@EnableApolloConfig(ApolloConfigConsts.NAMESPACE_TIKU_MONGO)
@Configuration
public class MongoDbConfig {
}
