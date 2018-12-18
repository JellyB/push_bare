package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.huatu.tiku.push.cast.*;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 下午10:20
 **/

@Slf4j
public class CustomFileCastTest extends PushBaseTest{


    @Autowired
    private AndroidCustomCast androidCustomCast;

    @Autowired
    private IosCustomCast iosCustomCast;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpClientStrategy httpClientStrategy;

    @Test
    public void testAndroidUpload(){
        try{
            long classId = 60722l;
            String key = NoticePushRedisKey.getCourseClassId(classId);
            Set<Integer> users = redisTemplate.opsForSet().members(key);
            users.add(233982024);
            List<Long> userIds = Lists.newArrayList();
            userIds.add(233982024L);
            userIds.add(233982179L);
            String alias = Joiner.on("\n").join(users);
            log.info("alias:{},", alias);
            String fileId = androidCustomCast.uploadContents(alias);
            log.info("android upload file:{}", fileId);
        }catch (Exception e){
            log.error("upload failed!");
        }
    }


    @Test
    public void androidCustomFileCast(){
        try{
            String fileId = "PF257041543286228983";
            androidCustomCast.setFileId( fileId, UmengNotification.ALIAS_TYPE);
            androidCustomCast.setTicker( "Android filecast ticker");
            androidCustomCast.setTitle(  "安卓文件发送样例");
            androidCustomCast.setText(   "安卓文件发送样例安卓文件发送样例安卓文件发送样例安卓文件发送样例");
            androidCustomCast.goAppAfterOpen();
            androidCustomCast.setDisplayType(AbstractAndroidNotification.DisplayType.NOTIFICATION);
            PushResult pushResult = httpClientStrategy.send(androidCustomCast);
            log.info("push result:{}", JSONObject.toJSONString(pushResult));
        }catch (Exception e){
            log.error("android custom file cast error!", e);
        }
    }

    @Test
    public void getAndroidCustomFileTaskStatus(){
        String taskId = "uccybcq154297891580801";
        String result = androidCustomCast.getTaskStatus(taskId);
        log.info("task status:{}", result);
    }


    @Test
    public void testIOSUpload(){
        try{
            long classId = 86980l;
            String key = NoticePushRedisKey.getCourseClassId(classId);
            Set<Integer> users = redisTemplate.opsForSet().members(key);
            users.add(233982024);
            String alias = Joiner.on("\n").join(users);
            log.info("alias:{},", alias);
            String fileId = iosCustomCast.uploadContents(alias);
            log.info("IOS upload file:{}", fileId);
        }catch (Exception e){
            log.error("IOS failed!");
        }
    }


    @Test
    public void testIosCustomFileCast(){
        try{
            String fileId = "PF901611542971404152";
            iosCustomCast.setFileId(fileId, UmengNotification.ALIAS_TYPE);
            iosCustomCast.setBadge( 1);
            iosCustomCast.setSound( "default");
            iosCustomCast.setAlertTitle("ios 自定义文件推送测试");
            iosCustomCast.setAlertBody("你好这是一条测试数据请忽略");
            iosCustomCast.setCustomizedField("type", "ztk://helloworld");
            // TODO set 'production_mode' to 'true' if your app is under production mode
            iosCustomCast.setTestMode();
            PushResult pushResult = httpClientStrategy.send(iosCustomCast);
            log.info("push result:{}", JSONObject.toJSONString(pushResult));
        }catch (Exception e){
            log.error("ios custom file cast error!", e);
        }
    }


    @Test
    public void getIosCustomFileTaskStatus(){
        String taskId = "ucxsnus154323822674800";
        String result = iosCustomCast.getTaskStatus(taskId);
        log.info("task status:{}", result);
    }


}
