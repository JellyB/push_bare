package com.huatu.tiku.push;

import com.huatu.tiku.push.constant.CorrectReportInfo;
import com.huatu.tiku.push.constant.CorrectReturnInfo;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import com.huatu.tiku.push.service.api.CorrectReportService;
import com.huatu.tiku.push.service.api.CorrectReturnService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-08-02 1:51 PM
 **/

@Slf4j
public class CorrectServiceTest extends PushBaseTest{
    @Autowired
    private CorrectReturnService correctReturnService;

    @Autowired
    private CorrectReportService correctReportService;


    @Test
    public void correctReturn(){

        CorrectReturnInfo correctReturnInfo = new CorrectReturnInfo();
        correctReturnInfo.setAnswerCardId(10001l);
        correctReturnInfo.setBizId(100001);
        correctReturnInfo.setDealDate(new Date());
        correctReturnInfo.setReturnContent("字迹潦草，破烂不堪");
        correctReturnInfo.setNoticeType(NoticeTypeEnum.CORRECT_RETURN);
        correctReturnInfo.setSubmitTime(new Date());
        correctReturnInfo.setUserId(1000131234);
        correctReturnService.sendCorrectNotice(correctReturnInfo);

    }

    @Test
    public void correctReport(){

        CorrectReportInfo correctReportInfo = new CorrectReportInfo();
        correctReportInfo.setAnswerCardId(10001l);
        correctReportInfo.setBizId(100001);
        correctReportInfo.setDealDate(new Date());
        correctReportInfo.setNoticeType(NoticeTypeEnum.CORRECT_REPORT);
        correctReportInfo.setQuestionName("套题");
        correctReportInfo.setSubmitTime(new Date());
        correctReportInfo.setUserId(1000131234);
        correctReportService.sendCorrectNotice(correctReportInfo);

    }
}
