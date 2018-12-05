package com.huatu.tiku.push.spring.conf;

import com.huatu.tiku.push.interceptor.TableSplitInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-03 下午3:49
 **/
@Configuration
@MapperScan(value = "com.huatu.tiku.push.dao")
public class MybatisConfig {


    @Autowired
    private HikariDataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory()
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        TableSplitInterceptor tableSplitInterceptor = new TableSplitInterceptor();
        bean.setPlugins(new Interceptor[] { tableSplitInterceptor });
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
