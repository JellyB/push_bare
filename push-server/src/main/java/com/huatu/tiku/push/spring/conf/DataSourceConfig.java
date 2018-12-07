package com.huatu.tiku.push.spring.conf;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 上午10:10
 **/

@Configuration
public class DataSourceConfig {

    /**
     * push server 数据源配置 master
     * @return
     */
    @Bean(value = "hikariDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public HikariDataSource hikariDataSource(){
        return (HikariDataSource)DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * quartz 数据源配置--select jobs
     */
    @Value("${quartz.dataSource.qzDS.URL}")
    private String url;

    @Value("${quartz.dataSource.qzDS.user}")
    private String username;

    @Value("${quartz.dataSource.qzDS.password}")
    private String password;

    @Bean(value = "quartHikariDataSource")
    public HikariDataSource quartHikariDataSource(){
        return (HikariDataSource)DataSourceBuilder
                .create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url(url)
                .username(username)
                .password(password)
                .type(HikariDataSource.class).build();

    }
}
