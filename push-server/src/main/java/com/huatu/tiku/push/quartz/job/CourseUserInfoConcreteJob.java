package com.huatu.tiku.push.quartz.job;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.CourseQueueEntity;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.service.api.CourseUserInfoComponent;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-15 下午10:30
 **/

@Slf4j
public class CourseUserInfoConcreteJob implements BaseQuartzJob {


    @Autowired
    private CourseUserInfoComponent courseUserInfoComponent;

    public  static final String BIZ_DATA = "CourseInfo";

    /**
     * execute
     *
     * @param executionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext executionContext) throws JobExecutionException {
        CourseInfo courseInfo = JSONObject.parseObject(executionContext.getJobDetail().getJobDataMap().getString(BIZ_DATA),CourseInfo.class);
        CourseQueueEntity courseQueueEntity = CourseQueueEntity.builder()
                .classId(courseInfo.getClassId())
                .liveId(courseInfo.getLiveId())
                .startTime(courseInfo.getStartTime())
                .endTime(courseInfo.getEndTime())
                .dealPage(new AtomicInteger(CourseQueueEntity.DEFAULT_PAGE))
                .build();

        courseUserInfoComponent.fetchUserName(courseQueueEntity);
    }
}
