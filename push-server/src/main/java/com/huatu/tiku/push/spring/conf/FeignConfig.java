package com.huatu.tiku.push.spring.conf;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-03 下午3:48
 **/

@Configuration
@EnableFeignClients({ "com.huatu.ic.tiku.biz.client", "com.huatu.ic.userservice.biz.service.feign" })
public class FeignConfig {
}
