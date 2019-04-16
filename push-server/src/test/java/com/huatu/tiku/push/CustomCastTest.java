package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.cast.AbstractAndroidNotification;
import com.huatu.tiku.push.cast.AndroidCustomCast;
import com.huatu.tiku.push.cast.HttpClientStrategy;
import com.huatu.tiku.push.cast.IosCustomCast;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 描述：自定义推送test
 *
 * @author biguodong
 * Create time 2018-11-14 下午3:16
 **/
@Slf4j
public class CustomCastTest  extends PushBaseTest{

    @Autowired
    private HttpClientStrategy httpClientStrategy;

    @Autowired
    private IosCustomCast iosCustomCast;

    @Autowired
    private AndroidCustomCast androidCustomCast;



    @Test
    public void testAndroidCustom(){
        try {
            androidCustomCast.setAlias("234934290", "personID");
            androidCustomCast.setTicker( "新的消息通知！");
            androidCustomCast.setTitle(  "22:00定时发送通知");
            androidCustomCast.setText(   "很高兴见到各位！！！！");
            androidCustomCast.goAppAfterOpen();
            /**
             * YYYY-MM-DD hh:mm:ss
             */
            //androidCustomCast.setPlaySound(true);
            androidCustomCast.goUrlAfterOpen("http://www.baidu.com");
            //androidCustomCast.setStartTime("2018-11-14 21:50:00");
            androidCustomCast.setDescription("一条安卓推送通知！");
            androidCustomCast.setDisplayType(AbstractAndroidNotification.DisplayType.NOTIFICATION);
            JSONObject custom = new JSONObject();
            custom.put("type", 1);
            androidCustomCast.setCustomField(custom);
            androidCustomCast.setProductionMode();
            // For how to register a test device, please see the developer doc.
            //androidCustomCast.setProductionMode(false);
            httpClientStrategy.send(StringUtils.EMPTY,androidCustomCast);
        }catch (Exception e){
            log.error("android custom push error ", e);
        }

    }

    @Test
    public void testIosCustom(){
        try{
            iosCustomCast.setAlias("234934290", "personID");
            iosCustomCast.setBadge( 1);
            iosCustomCast.setSound( "default");
            iosCustomCast.setAlertTitle("ios消息测试");
            iosCustomCast.setAlertBody("ios消息测试");
            iosCustomCast.setCustomizedField("type","ht://noticeCenter");
            // TODO set 'production_mode' to 'true' if your app is under production mode
            iosCustomCast.setProductionMode();
            httpClientStrategy.send(StringUtils.EMPTY, iosCustomCast);
        }catch (Exception e){
            log.error("ios custom push error", e);
        }
    }


    @Test
    public void sendAndroidUnicast() throws Exception {
        // TODO Set your device token
        androidCustomCast.setPredefinedKeyValue("type", "unicast");
        //androidCustomCast.setDeviceToken( "Aro0YSeWNKmpoxsE2N1QOnzDR2Gw_R3uYhUSHoZ-DUas");
        androidCustomCast.setTicker( "tiker阿斯顿发斯蒂芬");
        androidCustomCast.setTitle(  "单推title");
        androidCustomCast.setText(   "Android unicast text");
        androidCustomCast.goAppAfterOpen();
        androidCustomCast.setDisplayType(AbstractAndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        /**
         * For how to register a test device, please see the developer doc.
         */
        androidCustomCast.setProductionMode();
        /**
         * Set customized fields
         */
        androidCustomCast.setExtraField("test", "helloworld");
        httpClientStrategy.send(StringUtils.EMPTY, androidCustomCast);
    }


}
