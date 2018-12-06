package com.huatu.tiku.push.dao;


import com.huatu.tiku.push.entity.quartz.JobAndTriggers;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-11 下午6:46
 **/
public class JobAndTriggersMapper implements RowMapper<JobAndTriggers> {

    /**
     * Implementations must implement this method to map each row of data
     * in the ResultSet. This method should not call {@code next()} on
     * the ResultSet; it is only supposed to map values of the current row.
     *
     * @param rs     the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row
     * @throws SQLException if a SQLException is encountered getting
     *                      column values (that is, there's no need to catch SQLException)
     */
    @Override
    public JobAndTriggers mapRow(ResultSet rs, int rowNum) throws SQLException {
        return  JobAndTriggers
                .builder()
                .jobName(rs.getString("JOB_NAME"))
                .jobGroup(rs.getString("JOB_GROUP"))
                .jobClassName(rs.getString("JOB_CLASS_NAME"))
                .description(rs.getString("DESCRIPTION"))
                .jobBigData(rs.getBlob("JOB_BIG_DATA"))
                .triggerName(rs.getString("TRIGGER_NAME"))
                .triggerGroup(rs.getString("TRIGGER_GROUP"))
                .triggerState(rs.getString("TRIGGER_STATE"))
                .triggerType(rs.getString("TRIGGER_TYPE"))
                .startTime(rs.getLong("START_TIME"))
                .endTime(rs.getLong("END_TIME"))
                .nextFireTime(rs.getLong("NEXT_FIRE_TIME"))
                .prevFireTime(rs.getLong("PREV_FIRE_TIME"))
                .triggerBigData(rs.getBlob("TRIGGER_BIG_DATA"))
                .repeatCount(rs.getLong("REPEAT_COUNT"))
                .repeatInterval(rs.getLong("REPEAT_INTERVAL"))
                .timesTriggered(rs.getLong("TIMES_TRIGGERED"))
                .cronExpression(rs.getString("CRON_EXPRESSION"))
                .timeZoneId(rs.getString("TIME_ZONE_ID"))
                .build();
    }
}
