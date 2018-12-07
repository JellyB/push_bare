package com.huatu.tiku.push.spring.conf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


/**
 * @author biguodong
 */
@Configuration
public class QuartzTemplateConfig {


    @Autowired
    @Qualifier(value = "driverManagerDataSource")
    private DriverManagerDataSource driverManagerDataSource;

    @Bean("quartzJdbcTemplate")
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(driverManagerDataSource);
    }
}
