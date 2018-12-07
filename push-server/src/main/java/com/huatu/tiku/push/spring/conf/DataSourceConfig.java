package com.huatu.tiku.push.spring.conf;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author biguodong
 */
@Configuration
public class DataSourceConfig {

    @Bean(value = "hikariDataSource")
    public HikariDataSource hikariDataSource(){
        return new HikariDataSource();
    }

    @Value("${quartz.dataSource.qzDS.URL}")
    private String dataSourceQzDSURL;

    @Value("${quartz.dataSource.qzDS.user}")
    private String dataSourceQzDSUser;

    @Value("${quartz.dataSource.qzDS.password}")
    private String dataSourceQzDSPassword;

    @Bean(value = "driverManagerDataSource")
    public DriverManagerDataSource driverManagerDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl(dataSourceQzDSURL);
        driverManagerDataSource.setUsername(dataSourceQzDSUser);
        driverManagerDataSource.setPassword(dataSourceQzDSPassword);
        return driverManagerDataSource;
    }

}
