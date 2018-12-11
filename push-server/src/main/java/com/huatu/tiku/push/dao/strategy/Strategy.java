package com.huatu.tiku.push.dao.strategy;

import java.util.Map;

/**
 * 描述：分表策略
 * @author biguodong
 * Create time 2018-12-03 下午8:52
 **/
public interface Strategy {

    /**
     * 消息关系表
     */
    String NOTICE_RELATION = "r_notice_user";

    String TABLE_NAME="table_name";

    String SEPARATOR = "_";

    String USER_ID = "userId";

    /**
     * 取模策略
     */
    String MOLD_STRATEGY = "moldStrategy";
    /**
     * 根据参数 params 转换得到分表后的表名
     * @param params
     * @return
     */
    String convert(Map<String, Object> params);
}
