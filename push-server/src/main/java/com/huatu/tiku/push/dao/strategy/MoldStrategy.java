package com.huatu.tiku.push.dao.strategy;

import com.google.common.base.Joiner;
import com.huatu.tiku.push.util.ThreadLocalManager;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述：取模策略
 *
 * @author biguodong
 * Create time 2018-12-03 下午9:07
 **/
@Component
public class MoldStrategy implements Strategy {

    /**
     * CONVERT
     *
     * @param params
     * @return
     */
    @Override
    public String convert(Map<String,Object> params) {

        String tableName = String.valueOf(params.get(Strategy.TABLE_NAME));
        Long userId = Long.valueOf(String.valueOf(ThreadLocalManager.getConsoleContext().getRequestHeader().get(Strategy.USER_ID)));
        long mold = userId.longValue() % 16;
        String mold_ = String.format("%02d",mold);
        return Joiner.on(SEPARATOR).join(tableName, mold_);
    }
}
