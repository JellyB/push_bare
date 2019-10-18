package com.huatu.tiku.push.web.controller.api;

import com.huatu.common.SuccessMessage;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.common.bean.user.UserSession;
import com.huatu.tiku.push.constant.CorrectCourseWorkPushInfo;
import com.huatu.tiku.push.constant.CorrectReportInfo;
import com.huatu.tiku.push.constant.CorrectReturnInfo;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReportService;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReturnService;
import com.huatu.tiku.push.service.api.CorrectReportService;
import com.huatu.tiku.push.service.api.CorrectReturnService;
import com.huatu.tiku.springboot.users.support.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-08-02 3:45 PM
 **/

@RestController
@RequestMapping(value = "correct")
public class CorrectController {

    @Autowired
    private CorrectReportService correctReportService;

    @Autowired
    private CorrectReturnService correctReturnService;

    @Autowired
    private CorrectCourseWorkReportService correctCourseWorkReportService;

    @Autowired
    private CorrectCourseWorkReturnService correctCourseWorkReturnService;


    /**
     * 批改退回
     * @param correctReturnInfo
     * @return
     * @throws BizException
     */
    @PostMapping(value = "return")
    public Object returnCorrect(@RequestBody CorrectReturnInfo correctReturnInfo) throws BizException{
        correctReturnService.sendCorrectNotice(correctReturnInfo);
        return SuccessMessage.create("创建成功");
    }


    /**
     * 批改出报告
     * @param correctReportInfo
     * @return
     * @throws BizException
     */
    @PostMapping(value = "report")
    public Object reportCorrect(@RequestBody CorrectReportInfo correctReportInfo) throws BizException{
        correctReportService.sendCorrectNotice(correctReportInfo);
        return SuccessMessage.create("创建成功");
    }


    /**
     * courseWork
     * @param pushInfo
     * @return
     * @throws BizException
     */
    @PostMapping(value = "courseWork")
    public Object courseWork(@RequestBody CorrectCourseWorkPushInfo pushInfo) throws BizException{
        if(pushInfo.getType().equals(CorrectCourseWorkPushInfo.RETURN)){
            correctCourseWorkReturnService.send(pushInfo);
        }
        if(pushInfo.getType().equals(CorrectCourseWorkPushInfo.REPORT)){
            correctCourseWorkReportService.send(pushInfo);
        }
        return SuccessMessage.create("创建成功");
    }


    @PostMapping(value = "batch")
    public Object courseWorkBatch(
            @Token UserSession userSession,
            @RequestParam(value = "bizId") long bizId,
            @RequestParam(value = "netClassId") long netClassId,
            @RequestParam(value = "lessonId") long lessonId,
            @RequestParam(value = "syllabusId") long syllabusId,
            @RequestParam(value = "count") int count) throws Exception{
        CorrectCourseWorkPushInfo correctCourseWorkPushInfo = new CorrectCourseWorkPushInfo();
        for (int i = 0; i < count; i++) {
            correctCourseWorkPushInfo.setBizId(bizId + i);
            correctCourseWorkPushInfo.setNetClassId(netClassId);
            correctCourseWorkPushInfo.setSyllabusId(syllabusId);
            correctCourseWorkPushInfo.setLessonId(lessonId);
            correctCourseWorkPushInfo.setType("N");
            correctCourseWorkPushInfo.setSubmitTime(new Date());
            correctCourseWorkPushInfo.setUserId(userSession.getId());
            correctCourseWorkPushInfo.setReturnContent("字迹潦草1232");
            correctCourseWorkPushInfo.setStem("2019 国考万人模考第" + i);
            correctCourseWorkPushInfo.setImg("http://upload.htexam.com/classimg/class/1569838608.jpg");
            correctCourseWorkPushInfo.setIsLive(1);
            correctCourseWorkReturnService.send(correctCourseWorkPushInfo);
            TimeUnit.MILLISECONDS.sleep(new Random(1000).nextLong());
            correctCourseWorkPushInfo.setBizId(bizId + count + i);
            correctCourseWorkPushInfo.setType("T");
            correctCourseWorkReportService.send(correctCourseWorkPushInfo);
        }
        return SuccessMessage.create("创建成功");
    }
}
