package com.huatu.tiku.push.web.controller.api;

import com.huatu.common.SuccessMessage;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.CorrectReportInfo;
import com.huatu.tiku.push.constant.CorrectReturnInfo;
import com.huatu.tiku.push.service.api.CorrectReportService;
import com.huatu.tiku.push.service.api.CorrectReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
