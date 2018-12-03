package com.huatu.tiku.push.spring.conf;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-03 下午3:49
 **/
@Configuration
@MapperScan(value = "com.huatu.tiku.push.dao")
public class MybatisConfig {
}
