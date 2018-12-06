package com.huatu.tiku.push.dingtalk;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-23 下午3:47
 **/

@Component
@Slf4j
public class DingTalkNotice {

    @Value("${notice.push.listener.hook}")
    private String webHook_token;


    public void notice(Msg msg){
        try{
            HttpClient httpclient = HttpClients.createDefault();

            HttpPost httppost = new HttpPost(webHook_token);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            log.info(JSONObject.toJSONString(msg));
            StringEntity se = new StringEntity(JSONObject.toJSONString(msg), "utf-8");
            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String result= EntityUtils.toString(response.getEntity(), "utf-8");
                log.info(result);
            }
        }catch (Exception e){
            log.error("webHook notice error!", e);
        }
    }
}
