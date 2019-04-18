package com.huatu.tiku.push.cast;

import com.huatu.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-17 上午11:18
 **/
@Slf4j
@Component(value = "restTemplateStrategy")
public class RestTemplateStrategy implements RestPushStrategy {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 单个消息推送
     * @param key
     * @param msg
     * @return
     * @throws BizException
     */
    @Override
    public PushResult send(String key, UmengNotification msg) throws BizException {
        return null;
    }

    /**
     * 上传文件推送
     *
     * @param appkey
     * @param appMasterSecret
     * @param contents
     * @return
     * @throws Exception
     */
    @Override
    public String uploadContents(String appkey, String appMasterSecret, String contents) throws Exception {
        return null;
    }
}
