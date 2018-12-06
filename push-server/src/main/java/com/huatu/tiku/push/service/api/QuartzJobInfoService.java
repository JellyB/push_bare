package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.service.api.impl.QuartzJobInfoServiceImpl;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午4:45
 **/
public interface QuartzJobInfoService {

    /**
     * 查询job 列表
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    Object list(int page, int size) throws BizException;


    /**
     * 根据名称和组获取job信息
     * @param jobName
     * @param jobGroup
     * @return
     * @throws BizException
     */
    Object selectByJobNameAndGroup(String jobName, String jobGroup) throws BizException;

    /**
     * 条件 job
     * @return
     * @throws BizException
     */
    Object addJobByName(String jobName) throws BizException;

    /**
     * 添加课程提醒job
     * @param courseJob
     * @return
     * @throws BizException
     */
    Object addCourseRemindJob(QuartzJobInfoServiceImpl.CourseJob courseJob) throws BizException;

    /**
     * 使用cron 调度添加job
     * @param jobName
     * @param cron
     * @return
     * @throws BizException
     */
    Object addJobByCron(String jobName, String cron)throws BizException;


    /**
     * job 自定义调度
     * @param jobInfo
     * @return
     * @throws BizException
     */
    void jobreschedule(QuartzJobInfoServiceImpl.JobInfo jobInfo)throws BizException;


    /**
     * 删除job
     * @param jobKeys
     * @param triggerKeys
     * @return
     * @throws BizException
     */
    void deleteJob(List<JobKey> jobKeys, List<TriggerKey> triggerKeys)throws BizException;


    /**
     * 根据业务数据逻辑删除
     * @param bizId
     * @throws BizException
     */
    void deleteJobByBizData(String bizId)throws BizException;
}
