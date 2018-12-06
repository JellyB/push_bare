package com.huatu.tiku.push.quartz;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import org.quartz.Scheduler;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午9:20
 **/
public abstract class JobCreator<E> {

    private Scheduler scheduler;

    public JobCreator(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 检查job是否创建
     * @param o
     * @return
     * @throws BizException
     */
    protected abstract boolean checkJobExist(E o) throws BizException;


    /**
     * 创建job
     * @param o
     * @return
     * @throws BizException
     */
    protected abstract int build(E o) throws BizException;


    public final int create(E o) throws BizException {
        if(checkJobExist(o)){
            throw new BizException(NoticePushErrors.JOB_EXIST);
        }
        return build(o);
    }
}
