package com.huatu.tiku.push.util;

import com.google.common.base.Joiner;
import com.huatu.tiku.push.dao.strategy.Strategy;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-03 下午9:33
 **/
public class SplitUtils {


    /**
     * 根据userId取模获取表名称
     * @param userId
     * @return
     */
    public static String getTableName(long userId){
        String tableName = Strategy.NOTICE_RELATION;
        long mold = userId%16;
        return Joiner.on("_").join(tableName, String.format("%2d", mold));
    }
}
