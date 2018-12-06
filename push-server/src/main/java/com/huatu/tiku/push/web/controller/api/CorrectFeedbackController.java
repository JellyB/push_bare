package com.huatu.tiku.push.web.controller.api;

import com.huatu.common.SuccessMessage;
import com.huatu.tiku.push.constant.CorrectFeedbackInfo;
import com.huatu.tiku.push.service.api.CorrectFeedbackService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-15 下午4:12
 **/
@RestController
@RequestMapping(value = "feedback")
public class CorrectFeedbackController {

    @Autowired
    private CorrectFeedbackService correctFeedbackService;


    @PostMapping(value = "correct")
    public Object pushCorrect(@RequestBody CorrectBatch batch){
         correctFeedbackService.sendCorrectNotice(batch.getList());
        return SuccessMessage.create("创建成功！");

    }


    @Data
    @AllArgsConstructor
    public static class CorrectBatch implements Serializable{
        private List<CorrectFeedbackInfo> list;
    }
}
