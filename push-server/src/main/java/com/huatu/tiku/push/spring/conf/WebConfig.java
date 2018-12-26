package com.huatu.tiku.push.spring.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 上午10:04
 **/
@Configuration
public class WebConfig{

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
