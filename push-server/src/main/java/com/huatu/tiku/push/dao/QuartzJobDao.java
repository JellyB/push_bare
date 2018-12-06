package com.huatu.tiku.push.dao;

import com.huatu.tiku.push.entity.quartz.JobAndTriggers;
import com.huatu.tiku.push.entity.quartz.QrtzJobDetails;
import com.huatu.tiku.push.entity.quartz.QrtzTriggers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-09 下午10:16
 **/
@Repository
@Slf4j
public class QuartzJobDao {

    @Autowired
    @Qualifier(value = "quartzJdbcTemplate")
    private JdbcTemplate quartzJdbcTemplate;


    /**
     * 获取quartz 分页数据
     * @param limitSql
     * @return
     */
    public List<JobAndTriggers> searchJobListByPage(String limitSql){
        List<JobAndTriggers> list = quartzJdbcTemplate.query(limitSql, new JobAndTriggersMapper());
        return list;
    }

    /**
     * 根据name和group 查询job信息
     * @param bizId
     * @return
     */
    public List<QrtzJobDetails> getJobDetail(String bizId){
        String sql = "SELECT JOB_NAME,JOB_GROUP,JOB_CLASS_NAME FROM QRTZ_JOB_DETAILS WHERE JOB_NAME = LIKE :name";
        List<QrtzJobDetails> list = quartzJdbcTemplate.query(sql, new Object[]{ "%" + bizId}, new JobDetailMapper());
        return list;
    }

    /**
     * 根据trigger name 和 trigger group 查询 trigger信息
     * @return
     */
    public List<QrtzTriggers> getTrigger(String bizId){
        String sql = "SELECT TRIGGER_NAME,TRIGGER_GROUP,JOB_NAME,JOB_GROUP,START_TIME,END_TIME,NEXT_FIRE_TIME,PREV_FIRE_TIME,TRIGGER_TYPE FROM QRTZ_TRIGGERS WHERE TRIGGER_NAME like ?";
        List<QrtzTriggers> list = quartzJdbcTemplate.query(sql, new Object[]{ "%" + bizId}, new TriggersMapper());
        return list;
    }


    /**
     * 获取quartz total
     * @param countSql
     * @return
     */
    public long getQuartzTotal(String countSql){
        long total = quartzJdbcTemplate.queryForObject(countSql, Long.class);
        log.info("quartz total:{}", total);
        return total;
    }



}
