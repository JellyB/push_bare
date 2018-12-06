package com.huatu.tiku.push.spring.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-10 上午10:40
 **/
@Configuration
public class QuartzDataSourceConfig {


    @Value("${quartz.dataSource.qzDS.URL}")
    private String dataSourceQzDSURL;

    @Value("${quartz.dataSource.qzDS.user}")
    private String dataSourceQzDSUser;

    @Value("${quartz.dataSource.qzDS.password}")
    private String dataSourceQzDSPassword;

    @Bean
    public DriverManagerDataSource getDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl(dataSourceQzDSURL);
        driverManagerDataSource.setUsername(dataSourceQzDSUser);
        driverManagerDataSource.setPassword(dataSourceQzDSPassword);
        return driverManagerDataSource;
    }

    @Bean("quartzJdbcTemplate")
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

}
