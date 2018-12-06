package com.huatu.tiku.push.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.dao.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-06 下午1:47
 **/

@Slf4j
public class SplitParamsUtil {

    private static final String NOTICE_PANDORA_SEPARATOR = "00";

    /**
     * noticeId 转 userId 和 noticeId array
     * @param noticeId
     * @return
     */
    public static long[] obtainSplitArray(long noticeId){
        log.debug("noticeId:{}", noticeId);
        String noticeIdStr = String.valueOf(noticeId);
        if(!noticeIdStr.contains(NOTICE_PANDORA_SEPARATOR)){
            throw new BizException(NoticePushErrors.REMOVE_CALL_PARAM_ERROR);
        }
        String[] noticeArray = noticeIdStr.split(NOTICE_PANDORA_SEPARATOR);
        long[] noticeLongArray = new long[2];
        noticeLongArray[0] = Long.valueOf(noticeArray[0]);
        noticeLongArray[1] = Long.valueOf(noticeArray[1]);
        return noticeLongArray;
    }

    /**
     * userId + 00 + noticeId装成复合id
     */
    public static long buildComplexNoticeId(long userId, long noticeId){
        String complexId = Joiner.on(NOTICE_PANDORA_SEPARATOR).join(String.valueOf(userId), String.valueOf(noticeId));
        log.debug("complexId:{}", complexId);
        return Long.valueOf(complexId);
    }


    /**
     * 参与分表的参数放入thread local
     * @param userId
     */
    public static void putSplitParam2ThreadLocal(long userId){
        ConsoleContext consoleContext = ConsoleContext.getInstance();
        Map<String, Object> params = Maps.newHashMap();
        params.put(Strategy.USER_ID, userId);
        consoleContext.setRequestHeader(params);
        ThreadLocalManager.setConsoleContext(consoleContext);
    }
}
