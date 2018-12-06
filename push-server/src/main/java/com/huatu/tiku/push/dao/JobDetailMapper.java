package com.huatu.tiku.push.dao;


import com.huatu.tiku.push.entity.quartz.QrtzJobDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-19 下午7:32
 **/

public class JobDetailMapper implements RowMapper<QrtzJobDetails> {

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
    public QrtzJobDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return QrtzJobDetails
                .builder()
                .jobClassName(rs.getString("JOB_CLASS_NAME"))
                .jobGroup(rs.getString("JOB_GROUP"))
                .jobName(rs.getString("JOB_NAME"))
                .build();
    }
}
