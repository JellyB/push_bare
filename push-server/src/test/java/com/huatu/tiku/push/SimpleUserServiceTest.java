package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.cast.FileUploadTerminal;
import com.huatu.tiku.push.service.api.SimpleUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-06-06 9:45 AM
 **/
@Slf4j
public class SimpleUserServiceTest extends PushBaseTest{

    @Autowired
    private SimpleUserService simpleUserService;

    @Test
    public void fileObtainTest(){
        Long classId = 76295L;
        Long liveId = 34650L;
        FileUploadTerminal fileUploadTerminal = simpleUserService.obtainFileUpload(classId, liveId);
        log.info(">>>>>>>>>>>>:{}", JSONObject.toJSONString(fileUploadTerminal));

    }
}
