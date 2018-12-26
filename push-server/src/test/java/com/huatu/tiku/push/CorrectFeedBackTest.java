package com.huatu.tiku.push;

import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;
import com.huatu.tiku.push.service.api.CorrectFeedbackService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 描述：试题纠错
 *
 * @author biguodong
 * Create time 2018-11-14 上午10:35
 **/

public class CorrectFeedBackTest extends PushBaseTest{

    @Autowired
    private CorrectFeedbackService correctFeedbackService;

    public static final SimpleDateFormat wholeDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @Test
    public void test(){
        List<CorrectFeedbackInfo> correctFeedbackInfoList = Lists.newArrayList();

        correctFeedbackInfoList.add(CorrectFeedbackInfo.builder()
                .bizId(100L)
                .dealDate(new Date())
                .questionId(1000124L)
                .userId(233982024L)
                .source(wholeDateFormat.format(new Date()) + "送金币1 ")
                .gold(10)
                .reply("纠错送金币测试！").build());

        correctFeedbackInfoList.add(CorrectFeedbackInfo.builder()
                .bizId(101L)
                .dealDate(new Date())
                .questionId(1000124L)
                .userId(233982179L)
                .source(wholeDateFormat.format(new Date()) + "送金币2 ")
                .reply("纠错送金币测试！").build());

        correctFeedbackInfoList.add(CorrectFeedbackInfo.builder()
                .bizId(102L)
                .dealDate(new Date())
                .questionId(1000124L)
                .source(wholeDateFormat.format(new Date()) + "送金币3 ")
                .reply("纠错送金币测试！").build());


        correctFeedbackService.sendCorrectNotice(correctFeedbackInfoList);
    }

    @Test
    public void replyAndGoldEmptyTest(){
        List<CorrectFeedbackInfo> correctFeedbackInfoList = Lists.newArrayList();
        correctFeedbackInfoList.add(CorrectFeedbackInfo.builder()
                .bizId(102L)
                .userId(233982024L)
                .dealDate(new Date())
                .questionId(1000124L)
                .source("")
                .reply("")
                .gold(0)
                .build());
        correctFeedbackService.sendCorrectNotice(correctFeedbackInfoList);
    }

    @Test
    public void replyEmptyGoldNotEmptyTest(){
        List<CorrectFeedbackInfo> correctFeedbackInfoList = Lists.newArrayList();
        correctFeedbackInfoList.add(CorrectFeedbackInfo.builder()
                .bizId(102L)
                .userId(233982024L)
                .dealDate(new Date())
                .questionId(1000124L)
                .source("")
                .reply("")
                .gold(10)
                .build());
        correctFeedbackService.sendCorrectNotice(correctFeedbackInfoList);
    }


    @Test
    public void replyNotEmptyGoldNotEmptyTest(){
        List<CorrectFeedbackInfo> correctFeedbackInfoList = Lists.newArrayList();
        correctFeedbackInfoList.add(CorrectFeedbackInfo.builder()
                .bizId(102L)
                .userId(233982024L)
                .dealDate(new Date())
                .questionId(1000124L)
                .source("2017年新疆公务员《行测》真题（网友回忆）第73题")
                .reply("您的纠错已收到，谢谢您的反馈~")
                .gold(10)
                .build());
        correctFeedbackService.sendCorrectNotice(correctFeedbackInfoList);
    }


}
