package com.huatu.tiku.push.quartz;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.quartz.constant.JobParent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 描述：job for command
 *
 * @author biguodong
 * Create time 2018-11-08 下午8:57
 **/
@Slf4j
public abstract class Command<E> {


    /**Command
     * 搜索业务数据
     * @return
     * @throws BizException
     */
    protected abstract List<E> searchBizData() throws BizException;

    /**
     * 判断是否为新job
     * @param e
     * @return
     * @throws BizException
     */
    protected abstract Optional<NoticeTypeEnum> judgeCurrentDataJobAvailableAndJobType(E e) throws BizException;

    /**
     * 更新job信息
     * @param e
     * @return
     * @throws BizException
     */
    protected abstract int updateEntityScannedStatus(E e) throws BizException;

    /**
     * 需要一个额外的job
     * @param e
     * @param startTime
     * @param noticeTypeEnum
     * @return
     * @throws BizException
     */
    protected abstract JobParent needExtraJob(NoticeTypeEnum noticeTypeEnum, Date startTime, E e) throws BizException;
    /**
     * 构建job
     * @param e
     * @param noticeTypeEnum
     * @return
     * @throws BizException
     */
    protected abstract JobParent createJob(NoticeTypeEnum noticeTypeEnum, E e) throws BizException;

    /**
     * scheduleJob
     * @param jobParent
     * @throws BizException
     */
    protected abstract void scheduleJob(JobParent jobParent) throws BizException;


    /**
     * service work
     */
    public final void work() throws BizException {
        try{
            if(CollectionUtils.isEmpty(searchBizData())){
                return;
            }
            searchBizData().forEach(bizData->{
                Optional<NoticeTypeEnum> optionalNoticeTypeEnum = judgeCurrentDataJobAvailableAndJobType(bizData);
                if(optionalNoticeTypeEnum.isPresent()){

                    JobParent jobParent = createJob(optionalNoticeTypeEnum.get(), bizData);
                    scheduleJob(jobParent);
                    JobParent jobExtra = needExtraJob(optionalNoticeTypeEnum.get(), jobParent.getTrigger().getStartTime(), bizData);
                    scheduleJob(jobExtra);
                }else{
                    return;
                }
            });
        }catch (Exception e){
           log.info("command job work caught an exception!", e);
        }
    }
}
