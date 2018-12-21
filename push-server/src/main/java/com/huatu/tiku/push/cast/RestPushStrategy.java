package com.huatu.tiku.push.cast;

import com.huatu.common.exception.BizException;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-17 上午11:15
 **/
public interface RestPushStrategy {

    String HOST = "http://msg.umeng.com";

    String UPLOAD_PATH = "/upload";

    String POST_PATH = "/api/send";

    /**
     * 单个消息推送
     * @param msg
     * @return
     * @throws BizException
     */
    PushResult send(UmengNotification msg) throws BizException;


    /**
     * 上传文件推送
     * @param appkey
     * @param appMasterSecret
     * @param contents
     * @return
     * @throws Exception
     */
    String uploadContents(String appkey,String appMasterSecret,String contents) throws Exception;
}
