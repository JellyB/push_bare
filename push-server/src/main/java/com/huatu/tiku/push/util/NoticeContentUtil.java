package com.huatu.tiku.push.util;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.dingtalk.LinkMsg;
import com.huatu.tiku.push.dingtalk.TextMsg;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.quartz.job.BossJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.Date;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-23 下午7:25
 **/

@Slf4j
public class NoticeContentUtil {


    private static final String NEW_JOB = new StringBuilder("新的任务:")
            .append("\n")
            .append("\n")
            .append("名:%s")
            .append("\n")
            .append("组:%s")
            .append("\n")
            .append("执行时间:[%s]").toString();

    private static final String JOB_CANCEL = new StringBuilder("任务被取消:")
            .append("\n")
            .append("\n")
            .append("名:%s")
            .append("\n")
            .append("组:%s").toString();

    private static final String JOB_FINISH = new StringBuilder("任务执行完毕了:")
            .append("\n")
            .append("\n")
            .append("名:%s")
            .append("\n")
            .append("组:%s")
            .append("\n")
            .append("结束时间:[%s]").toString();

    private static final String JOB_FIRED = new StringBuilder("任务即将执行:")
            .append("\n")
            .append("\n")
            .append("名:%s")
            .append("\n")
            .append("组:%s")
            .append("\n")
            .append("即将在:[%s]执行，如有疑问请前往关闭！").toString();

    private static final String JOB_COMPLETE = new StringBuilder("任务执行完成:")
            .append("\n")
            .append("\n")
            .append("名:%s")
            .append("\n")
            .append("组:%s")
            .append("\n")
            .append("在:[%s]执行过了，如有疑问请前往关闭！").toString();


    private static final String CREATE_ERROR = new StringBuilder("创建消息异常:")
            .append("\n")
            .append("\n")
            .append("方法:%s")
            .append("\n")
            .append("类:%s")
            .append("\n")
            .append("请检查代码逻辑！").toString();

    /**
     * 新的jobSchedule
     * @param trigger
     * @return
     */
    public static TextMsg jobScheduled(Trigger trigger){
        try{
            String name = trigger.getJobKey().getName();
            String group = trigger.getJobKey().getGroup();
            Date nextFireTime = trigger.getNextFireTime();
            String content = String.format(NEW_JOB, name, group, NoticeTimeParseUtil.localDateFormat.format(nextFireTime));
            TextMsg textMsg = TextMsg.builder().content(content).atAll(false).mobiles("15727393776").build();
            return textMsg;
        }catch(Exception e){
            log.error("parse msg jobScheduled error, trigger msg:{}", JSONObject.toJSONString(trigger));
            return exceptionMst("jobScheduled", NoticeContentUtil.class.getSimpleName());
        }
    }

    /**
     * job 被取消
     * @param triggerKey
     * @return
     */
    public static TextMsg jobUnscheduled(TriggerKey triggerKey){
        try{
            String name = triggerKey.getName();
            String group = triggerKey.getGroup();
            String content = String.format(JOB_CANCEL, name, group);
            TextMsg textMsg = TextMsg.builder().content(content).atAll(false).mobiles("15727393776").build();
            return textMsg;
        }catch (Exception e){
            log.error("parse msg jobUnscheduled error, TriggerKey msg:{}", JSONObject.toJSONString(triggerKey));
            return exceptionMst("jobUnscheduled", NoticeContentUtil.class.getSimpleName());
        }
    }

    /**
     * job 执行完毕
     * @param trigger
     * @return
     */
    public static TextMsg triggerFinalized(Trigger trigger){
        try{
            String name = trigger.getJobKey().getName();
            String group = trigger.getJobKey().getGroup();
            Date endTime = trigger.getPreviousFireTime();
            String content = String.format(JOB_FINISH, name, group, NoticeTimeParseUtil.localDateFormat.format(endTime));
            TextMsg textMsg = TextMsg.builder().content(content).atAll(false).mobiles("15727393776").build();
            return textMsg;
        }catch (Exception e){
            log.error("parse triggerFinalized msg error, trigger msg:{},error:{}", JSONObject.toJSONString(trigger), e);
            return exceptionMst("triggerFinalized", NoticeContentUtil.class.getSimpleName());
        }
    }

    /**
     * 任务即将执行
     * @param trigger
     * @return
     */
    public static TextMsg triggerFired(Trigger trigger){
        try{
            String name = trigger.getJobKey().getName();
            String group = trigger.getJobKey().getGroup();
            Date date;
            if(null == trigger.getNextFireTime()){
                date = NoticeTimeParseUtil.localDateFormat.parse("1990-01-01 00:00:00");
            }else{
                date = trigger.getNextFireTime();
            }
            String content = String.format(JOB_FIRED, name, group, NoticeTimeParseUtil.localDateFormat.format(date));
            if(name.equals(BossJob.BossJob)){
                return null;
            }
            TextMsg textMsg = TextMsg.builder().content(content).mobiles("15727393776").atAll(false).build();
            return textMsg;
        }catch (Exception e){
            log.error("construct text msg error", e);
            return exceptionMst("triggerFired", NoticeContentUtil.class.getSimpleName());
        }
    }

    /**
     * 任务执行完
     * @param trigger
     * @return
     */
    public static TextMsg triggerComplete(Trigger trigger){
        try{
            String name = trigger.getJobKey().getName();
            String group = trigger.getJobKey().getGroup();
            Date date = trigger.getEndTime() == null ? new Date(): trigger.getNextFireTime();
            String content = String.format(JOB_COMPLETE, name, group, NoticeTimeParseUtil.localDateFormat.format(date));
            if(name.equals(BossJob.BossJob)){
                return null;
            }
            TextMsg textMsg = TextMsg.builder().content(content).mobiles("15727393776").atAll(false).build();
            return textMsg;
        }catch (Exception e){
            log.error("parse triggerComplete msg error, trigger msg:{}", JSONObject.toJSONString(trigger));
            return exceptionMst("triggerComplete", NoticeContentUtil.class.getSimpleName());
        }
    }


    /**
     * 课程任务执行完成link消息
     * @param object
     * @param messageUrl
     * @return
     */
    public static LinkMsg triggerCompleteLinkMsgCourseInfo(Object object, String messageUrl){
        try{
            CourseInfo courseInfo = (CourseInfo) object;
            LinkMsg linkMsg = LinkMsg.builder()
                    .messageUrl(messageUrl)
                    .picUrl(courseInfo.getClassImg())
                    .title(courseInfo.getClassTitle())
                    .text(courseInfo.getSection())
                    .build();

            return linkMsg;
        }catch (Exception e){
            log.error("parse triggerComplete msg error, trigger object:{},messageUrl:{}", JSONObject.toJSONString(object), messageUrl);
        }
        return null;
    }

    /**
     * 异常情况下创建消息通知
     * @param methodName
     * @param className
     * @return
     */
    public static TextMsg exceptionMst(String methodName, String className){
        String content = String.format(CREATE_ERROR, methodName, className);
        TextMsg textMsg = TextMsg.builder().mobiles("15727393776").content(content).atAll(false).build();
        return textMsg;
    }

}
