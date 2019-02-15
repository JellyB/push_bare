package com.huatu.tiku.push.quartz.listener;

import com.huatu.tiku.push.dingtalk.DingTalkNotice;
import com.huatu.tiku.push.dingtalk.TextMsg;
import com.huatu.tiku.push.util.NoticeContentUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-23 下午4:08
 **/
@Slf4j
@Component
public class QuartzSchedulerListener implements SchedulerListener {

    @Autowired
    private DingTalkNotice dingTalkNotice;

    @Autowired
    private NoticeContentUtil noticeContentUtil;
    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link JobDetail}</code>
     * is scheduled.
     * </p>
     *
     * @param trigger
     */
    @Override
    public void jobScheduled(Trigger trigger) {
        TextMsg textMsg = noticeContentUtil.jobScheduled(trigger);
        dingTalkNotice.notice(textMsg);
    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link JobDetail}</code>
     * is unscheduled.
     * </p>
     *
     * @param triggerKey
     * @see SchedulerListener#schedulingDataCleared()
     */
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        TextMsg textMsg = noticeContentUtil.jobUnscheduled(triggerKey);
        dingTalkNotice.notice(textMsg);
    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
     * has reached the condition in which it will never fire again.
     * </p>
     *
     * @param trigger
     */
    @Override
    public void triggerFinalized(Trigger trigger) {
        TextMsg textMsg = noticeContentUtil.triggerFinalized(trigger);
        dingTalkNotice.notice(textMsg);
    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
     * has been paused.
     * </p>
     *
     * @param triggerKey
     */
    @Override
    public void triggerPaused(TriggerKey triggerKey) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a
     * group of <code>{@link Trigger}s</code> has been paused.
     * </p>
     * <p>
     * <p>If all groups were paused then triggerGroup will be null</p>
     *
     * @param triggerGroup the paused group, or null if all were paused
     */
    @Override
    public void triggersPaused(String triggerGroup) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
     * has been un-paused.
     * </p>
     *
     * @param triggerKey
     */
    @Override
    public void triggerResumed(TriggerKey triggerKey) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a
     * group of <code>{@link Trigger}s</code> has been un-paused.
     * </p>
     *
     * @param triggerGroup
     */
    @Override
    public void triggersResumed(String triggerGroup) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link JobDetail}</code>
     * has been added.
     * </p>
     *
     * @param jobDetail
     */
    @Override
    public void jobAdded(JobDetail jobDetail) {
        jobDetail.getKey().getName();
        jobDetail.getDescription();
    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link JobDetail}</code>
     * has been deleted.
     * </p>
     *
     * @param jobKey
     */
    @Override
    public void jobDeleted(JobKey jobKey) {
        jobKey.getName();
        jobKey.getClass();
    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link JobDetail}</code>
     * has been paused.
     * </p>
     *
     * @param jobKey
     */
    @Override
    public void jobPaused(JobKey jobKey) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a
     * group of <code>{@link JobDetail}s</code> has been paused.
     * </p>
     *
     * @param jobGroup the paused group, or null if all were paused
     */
    @Override
    public void jobsPaused(String jobGroup) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link JobDetail}</code>
     * has been un-paused.
     * </p>
     *
     * @param jobKey
     */
    @Override
    public void jobResumed(JobKey jobKey) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a
     * group of <code>{@link JobDetail}s</code> has been un-paused.
     * </p>
     *
     * @param jobGroup
     */
    @Override
    public void jobsResumed(String jobGroup) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a serious error has
     * occurred within the scheduler - such as repeated failures in the <code>JobStore</code>,
     * or the inability to instantiate a <code>Job</code> instance when its
     * <code>Trigger</code> has fired.
     * </p>
     * <p>
     * <p>
     * The <code>getErrorCode()</code> method of the given SchedulerException
     * can be used to determine more specific information about the type of
     * error that was encountered.
     * </p>
     *
     * @param msg
     * @param cause
     */
    @Override
    public void schedulerError(String msg, SchedulerException cause) {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> to inform the listener
     * that it has move to standby mode.
     * </p>
     */
    @Override
    public void schedulerInStandbyMode() {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> to inform the listener
     * that it has started.
     * </p>
     */
    @Override
    public void schedulerStarted() {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> to inform the listener
     * that it is starting.
     * </p>
     */
    @Override
    public void schedulerStarting() {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> to inform the listener
     * that it has shutdown.
     * </p>
     */
    @Override
    public void schedulerShutdown() {

    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> to inform the listener
     * that it has begun the shutdown sequence.
     * </p>
     */
    @Override
    public void schedulerShuttingdown() {

    }

    /**
     * Called by the <code>{@link Scheduler}</code> to inform the listener
     * that all jobs, triggers and calendars were deleted.
     */
    @Override
    public void schedulingDataCleared() {

    }
}
