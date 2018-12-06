package com.huatu.tiku.push.quartz.listener;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.dingtalk.DingTalkNotice;
import com.huatu.tiku.push.dingtalk.LinkMsg;
import com.huatu.tiku.push.dingtalk.TextMsg;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.quartz.job.BaseQuartzJob;
import com.huatu.tiku.push.util.JobKeyUtil;
import com.huatu.tiku.push.util.NoticeContentUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-23 下午3:29
 **/

@Slf4j
@Component
public class QuartzTriggerListener implements TriggerListener {

    @Value("${notice.push.result.url}")
    private String pushResultUrl;

    @Autowired
    private DingTalkNotice dingTalkNotice;

    /**
     * <p>
     * Get the name of the <code>TriggerListener</code>.
     * </p>
     */
    @Override
    public String getName() {
        return "quartz trigger 任务监听器";
    }

    /**
     * Trigger被激发 它关联的job即将被运行
     * @param trigger The <code>Trigger</code> that has fired.
     * @param context The <code>JobExecutionContext</code> that will be passed to
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        TextMsg textMsg = NoticeContentUtil.triggerFired(trigger);
        if(null != textMsg){
            dingTalkNotice.notice(textMsg);
        }
    }

    /**
     * Trigger被激发 它关联的job即将被运行,先执行(1)，在执行(2) 如果返回TRUE 那么任务job会被终止
     * @param trigger The <code>Trigger</code> that has fired.
     * @param context The <code>JobExecutionContext</code> that will be passed to
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.info("QuartzTriggerListener.vetoJobExecution()>>>>>>>>>>>");
        return false;
    }

    /**
     * (3) 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     *  那么有的触发器就有可能超时，错过这一轮的触发。
     * @param trigger The <code>Trigger</code> that has misfired.
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        log.error("QuartzTriggerListener.triggerMisfired()>>>>>>>>>>");
    }

    /**
     * (4) 任务完成时触发
     * @param trigger                The <code>Trigger</code> that was fired.
     * @param context                The <code>JobExecutionContext</code> that was passed to the
     *                               <code>Job</code>'s<code>execute(xx)</code> method.
     * @param triggerInstructionCode the result of the call on the <code>Trigger</code>'s<code>triggered(xx)</code>
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        try{
            if(JobKeyUtil.checkTriggerKey(trigger.getJobKey().getGroup())){
                JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
                Object object = null;
                if(jobDataMap.size() > 0){
                    String bizData = String.valueOf(jobDataMap.get(BaseQuartzJob.CourseBizData));
                    String clazz = String.valueOf(String.valueOf(jobDataMap.get(BaseQuartzJob.BizDataClass)));
                    object = JSONObject.parseObject(bizData, Class.forName(clazz));
                }
                Object[] params = JobKeyUtil.obtainLinkParams(trigger.getJobKey().getName());
                StringBuilder messageUrl = new StringBuilder(pushResultUrl)
                        .append("?type=")
                        .append(String.valueOf(params[0]))
                        .append("&detailType=")
                        .append(String.valueOf(params[1]))
                        .append("&bizId=")
                        .append(Long.valueOf(String.valueOf(params[2])));
                if(null != object && object instanceof CourseInfo){
                    LinkMsg linkMsg = NoticeContentUtil.triggerCompleteLinkMsgCourseInfo(object, messageUrl.toString());
                    if(null != linkMsg){
                        dingTalkNotice.notice(linkMsg);
                    }
                }

            }else{
                TextMsg textMsg = NoticeContentUtil.triggerComplete(trigger);
                if(null != textMsg){
                    dingTalkNotice.notice(textMsg);
                }
            }
        }catch (Exception e){
            log.error("QuartzTriggerListener.triggerComplete()>>>>>>>>>");
        }

    }
}
