package com.huatu.tiku.push.quartz.command;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.Command;
import com.huatu.tiku.push.quartz.constant.JobParent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-09 上午10:44
 **/
@Service
@Slf4j
public class MockJobCommand extends Command {
    /**
     * 搜索业务数据
     *
     * @return
     * @throws BizException
     */
    @Async
    @Override
    protected List searchBizData() throws BizException {
        return null;
    }

    /**
     * 判断是否为新job
     *
     * @param o
     * @return
     * @throws BizException
     */
    @Async
    @Override
    protected Optional<NoticeTypeEnum> judgeCurrentDataJobAvailableAndJobType(Object o) throws BizException {
        return Optional.of(null);
    }

    /**
     * 更新job信息
     *
     * @param o
     * @return
     * @throws BizException
     */
    @Override
    protected int updateEntityScannedStatus(Object o) throws BizException {
        return 0;
    }


    /**
     * 如果需要创建队列跑数据
     *
     * @param noticeTypeEnumDate
     * @param o
     * @param startTime
     * @throws BizException
     */
    @Override
    protected JobParent needExtraJob(NoticeTypeEnum noticeTypeEnumDate, Date startTime, Object o) throws BizException {
        return null;
    }

    /**
     * 构建job
     *
     * @param o
     * @throws BizException
     */
    @Async
    @Override
    protected JobParent createJob(NoticeTypeEnum noticeTypeEnum, Object o) throws BizException {
        return null;
    }


    /**
     * scheduleJob
     *
     * @param jobParent
     * @throws BizException
     */
    @Override
    protected void scheduleJob(JobParent jobParent) throws BizException {

    }
}
