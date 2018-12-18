package com.huatu.tiku.push.cast;

import com.huatu.tiku.push.quartz.factory.AbstractFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 描述：IOS 自定义推送
 *
 * @author biguodong
 * Create time 2018-11-14 下午3:02
 **/
@Service
@Slf4j
public class IosCustomCast extends AbstractIOSNotification{

    @Value("${notice.push.ios.appkey}")
    private String iosAppKey;

    @Value("${notice.push.ios.masterSecret}")
    private String iosMasterSecret;

    @Autowired
    private HttpClientStrategy pushClient;


    @PostConstruct
    public void IOSCustomCast() throws Exception {
        this.setAppMasterSecret(iosMasterSecret);
        this.setPredefinedKeyValue("appkey", iosAppKey);
        this.setPredefinedKeyValue("type", CUSTOM_CAST);

        AbstractFactory.iosCustomCast.setAppMasterSecret(iosMasterSecret);
        AbstractFactory.iosCustomCast.setPredefinedKeyValue("appkey", iosAppKey);
        AbstractFactory.iosCustomCast.setPredefinedKeyValue("type", CUSTOM_CAST);

        AbstractFactory.iosCustomFileCast.setAppMasterSecret(iosMasterSecret);
        AbstractFactory.iosCustomFileCast.setPredefinedKeyValue("appkey", iosAppKey);
        AbstractFactory.iosCustomFileCast.setPredefinedKeyValue("type", CUSTOM_CAST);
        if(isProduct()){
            AbstractFactory.iosCustomCast.setProductionMode();
            AbstractFactory.iosCustomFileCast.setProductionMode();
            this.setProductionMode();
        }else{
            AbstractFactory.iosCustomCast.setTestMode();
            AbstractFactory.iosCustomFileCast.setTestMode();
            this.setTestMode();
        }
    }

    /**
     *  别名推送 建议 < 500 使用
     * @param alias
     * @param aliasType
     * @throws Exception
     */
    public void setAlias(String alias,String aliasType) throws Exception {
        setPredefinedKeyValue("alias", alias);
        setPredefinedKeyValue("alias_type", aliasType);
    }

    /**
     * 自定义文件别名推送 < 10M
     * @param fileId
     * @param aliasType
     * @throws Exception
     */
    public void setFileId(String fileId, String aliasType) throws Exception {
        setPredefinedKeyValue("file_id", fileId);
        setPredefinedKeyValue("alias_type", aliasType);
    }



    /**
     * ios 上传多个alias 获取文件id
     * @param content
     * @return
     */
    @Deprecated
    public String uploadContents(String content){
        String fileId = "";
        try {
            fileId = pushClient.uploadContents(iosAppKey, iosMasterSecret, content);
        }catch (Exception e){
            log.error("ios notification upload file error!", e);
        }
        return fileId;
    }


    /**
     * 安卓获取任务执行结果
     * @param taskId
     * @return
     */
    @Deprecated
    public String getTaskStatus(String taskId){
        String result = "";
        try{
            result = pushClient.obtainTaskStatus(iosAppKey, iosMasterSecret, taskId);
        }catch (Exception e){
            log.error("obtain task status error!", e);
        }
        return result;
    }
}
