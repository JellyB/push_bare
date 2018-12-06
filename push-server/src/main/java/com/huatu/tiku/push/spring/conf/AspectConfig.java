package com.huatu.tiku.push.spring.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-06 下午4:05
 **/
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class AspectConfig {
}
