package com.huatu.tiku.push.listener.amq;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.CorrectCourseWorkPushInfo;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReportService;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：申论课后作业消息通知
 *
 * @author biguodong
 * Create time 2018-12-11 下午1:51
 **/
@Component
@RabbitListener(queues = RabbitMqKey.NOTICE_CORRECT_COURSE_WORK)
@Slf4j
public class CorrectCourseWorkListener {

    @Autowired
    private CorrectCourseWorkReturnService correctCourseWorkReturnService;

    @Autowired
    private CorrectCourseWorkReportService correctCourseWorkReportService;

    @RabbitHandler
    public void onMessage(String message){
        CorrectCourseWorkPushInfo pushInfo = JSONObject.parseObject(message, CorrectCourseWorkPushInfo.class);
        log.info("申论课后作业批改推送内容:{}", JSONObject.toJSONString(pushInfo));
        if(pushInfo.getType().equals(CorrectCourseWorkPushInfo.RETURN)){
            correctCourseWorkReturnService.send(pushInfo);
        }
        if(pushInfo.getType().equals(CorrectCourseWorkPushInfo.REPORT)){
            correctCourseWorkReportService.send(pushInfo);
        }
    }
}
