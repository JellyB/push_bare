package com.huatu.tiku.push.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 描述：友盟常用方法
 *
 * @author biguodong
 * Create time 2018-11-07 上午11:19
 **/

@Component
@Slf4j
public class UmengComponent {


    private static final String USER_AGNET = "Mozilla/5.0";


    private static final String HOST = "http://msg.umeng.com";

    private static final String UPLOAD_PATH = HOST + "/upload";

    private static final String SEND_PATH = HOST + "/api/send";

    //@Value("${umeng.appKey}")
    private String appKey;

    //@Value("${umeng.appMasterSecret}")
    private String appMasterSecret;


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 上传content 获取file_id
     * @param contents
     * @return
     * @throws Exception
     */
    public String uploadContentsObtainFileId(String contents) throws Exception {
        /**
         * Construct the json string
         */
        JSONObject uploadJson = new JSONObject();
        uploadJson.put("appkey", appKey);
        String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
        uploadJson.put("timestamp", timestamp);
        uploadJson.put("content", contents);

        String postBody = uploadJson.toString();
        String sign = DigestUtils.md5Hex(("POST" + UPLOAD_PATH + postBody + appMasterSecret).getBytes("utf8"));
        String url = UPLOAD_PATH + "?sign=" + sign;
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", USER_AGNET);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<String> exchange = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.POST, entity, String.class);
        String responseBody = exchange.getBody();
        log.info("response body:{}", responseBody);
        JSONObject respJson = JSONObject.parseObject(responseBody);
        String ret = respJson.getString("ret");
        if (!ret.equals("SUCCESS")) {
            throw new Exception("Failed to upload file");
        }
        JSONObject data = respJson.getJSONObject("data");
        String fileId = data.getString("file_id");
        return fileId;
    }


}
