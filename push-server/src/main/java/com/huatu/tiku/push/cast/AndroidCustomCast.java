package com.huatu.tiku.push.cast;

import com.huatu.tiku.push.quartz.factory.AbstractFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 描述：安卓自定义推送
 *
 * @author biguodong
 * Create time 2018-11-14 下午3:01
 **/

@Slf4j
@Service
public class AndroidCustomCast extends AbstractAndroidNotification{

    @Value("${notice.push.android.appkey}")
    private String androidAppKey;

    @Value("${notice.push.android.masterSecret}")
    private String androidMasterSecret;


    @Autowired
    private HttpClientStrategy pushClient;

    @PostConstruct
    public void AndroidCustomCast() throws Exception {
        this.setAppMasterSecret(androidMasterSecret);
        this.setPredefinedKeyValue("appkey", androidAppKey);
        this.setPredefinedKeyValue("type", CUSTOM_CAST);

        AbstractFactory.androidCustomCast.setAppMasterSecret(androidMasterSecret);
        AbstractFactory.androidCustomCast.setPredefinedKeyValue("appkey", androidAppKey);
        AbstractFactory.androidCustomCast.setPredefinedKeyValue("type", CUSTOM_CAST);
        AbstractFactory.androidCustomCast.setNoticePushEnv(getNoticePushEnv());
        AbstractFactory.androidCustomFileCast.setAppMasterSecret(androidMasterSecret);
        AbstractFactory.androidCustomFileCast.setPredefinedKeyValue("appkey", androidAppKey);
        AbstractFactory.androidCustomFileCast.setPredefinedKeyValue("type", CUSTOM_CAST);
        AbstractFactory.androidCustomFileCast.setNoticePushEnv(getNoticePushEnv());
        if(isProduct()){
            AbstractFactory.androidCustomCast.setProductionMode();
            AbstractFactory.androidCustomFileCast.setProductionMode();
            this.setProductionMode();
        }else{
            AbstractFactory.androidCustomCast.setTestMode();
            AbstractFactory.androidCustomFileCast.setTestMode();
            this.setTestMode();
        }
    }

    /**
     * 别名推送 建议 < 500 使用
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
    public void setFileId(String fileId,String aliasType) throws Exception {
        setPredefinedKeyValue("file_id", fileId);
        setPredefinedKeyValue("alias_type", aliasType);
    }



    /**
     * 安卓 上传file 获取 fileid
     * @param content
     * @return
     */
    @Deprecated
    public String uploadContents(String content){
        String fileId = "";
        try {
            fileId = pushClient.uploadContents(androidAppKey, androidMasterSecret, content);
        }catch (Exception e){
            log.error("android notification upload file error!", e);
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
            result = pushClient.obtainTaskStatus(androidAppKey, androidMasterSecret, taskId);
        }catch (Exception e){
            log.error("obtain task status error!", e);
        }
        return result;
    }


}
