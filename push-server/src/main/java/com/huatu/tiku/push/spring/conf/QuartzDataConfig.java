package com.huatu.tiku.push.spring.conf;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @author biguodong
 */
@Configuration
public class QuartzDataConfig {


    @Autowired
    @Qualifier(value = "quartHikariDataSource")
    private HikariDataSource quartHikariDataSource;

    @Bean("quartzJdbcTemplate")
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(quartHikariDataSource);
    }
}
