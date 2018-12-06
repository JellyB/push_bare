package com.huatu.tiku.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-03 下午2:19
 **/

@ComponentScan(basePackageClasses = PushApplication.class)
@EnableAutoConfiguration
@SpringBootConfiguration
public class PushApplication {

    public static void main(String[] args) {
        SpringApplication.run(PushApplication.class, args);
    }
}
