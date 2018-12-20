package com.huatu.tiku.push.web.controller.api;

import com.huatu.common.SuccessMessage;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.service.api.QuartzJobInfoService;
import com.huatu.tiku.push.service.api.impl.QuartzJobInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 下午4:37
 **/
@RestController
@RequestMapping(value = "job")
@Slf4j
public class QuartzJobController {



    @Autowired
    private QuartzJobInfoService quartzJobInfoService;
    /**

    /**
     * 新增任务
     *
     * @Title: add
     */
    @PostMapping(value = "add/{jobName}")
    public Object add(@PathVariable(value = "jobName") String jobName) {
        return quartzJobInfoService.addJobByName(jobName);
    }

    /**
     * add course remind job
     * @param courseJob
     * @return
     */
    @PostMapping(value = "addRemind")
    public Object addRemind(@RequestBody QuartzJobInfoServiceImpl.CourseJob courseJob) {
        return quartzJobInfoService.addCourseRemindJob(courseJob);
    }

    /**
     * add course ready job
     * @param courseJob
     * @return
     */
    @PostMapping(value = "addReady")
    public Object addReady(@RequestBody QuartzJobInfoServiceImpl.CourseJob courseJob) {
        return quartzJobInfoService.addCourseReadyJob(courseJob);
    }



    @PostMapping(value = "cron/{jobName}/{cron}")
    public Object cron(@PathVariable(value = "jobName") String jobName,
                       @PathVariable(value = "cron")String cron) {
        quartzJobInfoService.addJobByCron(jobName, cron);
        return SuccessMessage.create("创建成功！！");
    }


    /**
     * rescheduleJob
     * @param jobInfo
     */
    @PostMapping(value = "rescheduleJob")
    public Object rescheduleJob(@RequestBody QuartzJobInfoServiceImpl.JobInfo jobInfo){
        quartzJobInfoService.jobreschedule(jobInfo);
        return SuccessMessage.create("job 调度成功！");
    }



    /**
     * 获取job list
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    @GetMapping(value = "list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "20") int size) throws BizException {
        return quartzJobInfoService.list(page, size);
    }
}
