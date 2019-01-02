package com.huatu.tiku.push.spring.conf;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-01-02 下午2:42
 **/

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.master")
public class PrimaryDataSourceConfig extends HikariConfig{

    /**
     * push server 数据源配置 master
     * @return
     */
    @Bean(value = "hikariDataSource")
    @Primary
    public HikariDataSource hikariDataSource(){
        HikariDataSource hikariDataSource =  new HikariDataSource(this);
        return hikariDataSource;
    }
}
