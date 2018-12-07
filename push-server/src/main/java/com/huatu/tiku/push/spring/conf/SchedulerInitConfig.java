package com.huatu.tiku.push.spring.conf;

import com.huatu.tiku.push.quartz.listener.QuartzSchedulerListener;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午4:18
 **/

@Configuration
@Slf4j
public class SchedulerInitConfig {

    @Value("${quartz.dataSource.qzDS.URL}")
    private String dataSourceQzDSURL;

    @Value("${quartz.dataSource.qzDS.user}")
    private String dataSourceQzDSUser;

    @Value("${quartz.dataSource.qzDS.password}")
    private String dataSourceQzDSPassword;

    @Autowired
    private QuartzJobFactory quartzJobFactory;

    @Autowired
    private QuartzSchedulerListener quartzSchedulerListener;

    /**
     * 定时任务初始化监听器
     * @return
     */
    @Bean
    public QuartzInitializerListener initializerListener(){
        return new QuartzInitializerListener();
    }


    /**
     * 任务调度器
     * @return
     * @throws IOException
     */
    @Bean(name="Scheduler")
    public Scheduler scheduler() {
        try{
            Scheduler scheduler =  schedulerFactoryBean().getScheduler();
            scheduler.getListenerManager().addSchedulerListener(quartzSchedulerListener);
            return scheduler;
        }catch (Exception e){
            log.error("init Scheduler error!", e);
        }
        return null;

    }

    /**
     * 任务调度器工厂
     *
     * @Title: schedulerFactoryBean
     * @throws IOException
     */
    @Bean(name = "SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        factory.setOverwriteExistingJobs(true);
        factory.setStartupDelay(15);
        factory.setJobFactory(quartzJobFactory);
        return factory;
    }


    /**
     * 读取定时任务配置
     *
     * @Title: quartzProperties
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Properties properties = new Properties();
        properties.load(new ClassPathResource("/quartz.properties").getInputStream());
        properties.setProperty("org.quartz.dataSource.qzDS.URL", dataSourceQzDSURL);
        properties.setProperty("org.quartz.dataSource.qzDS.user", dataSourceQzDSUser);
        properties.setProperty("org.quartz.dataSource.qzDS.password", dataSourceQzDSPassword);
        propertiesFactoryBean.setProperties(properties);
        /*propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));*/
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


}
