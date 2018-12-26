package com.huatu.tiku.push.spring.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 上午10:04
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/push/pc/**");
    }
}
