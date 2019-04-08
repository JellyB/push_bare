package com.huatu.tiku.push.cast;

import com.alibaba.fastjson.JSONObject;
import com.huatu.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * @author biguodong
 *
 */

@Slf4j
@Component
@Primary
public class HttpClientStrategy implements RestPushStrategy {

	/**
	 * The user agent
	 */
	protected final String USER_AGENT = "Mozilla/5.0";

	/**
	 * The host
	 */
	protected static final String HOST = "http://msg.umeng.com";

	/**
	 * The upload path
	 */
	protected static final String UPLOAD_PATH = "/upload";

	/**
	 * The post path
	 */
	protected static final String POST_PATH = "/api/send";

	protected static final String TASK_STATUS = "/api/status";

	@Override
	public PushResult send(UmengNotification msg) throws BizException {
		HttpClient defaultHttpClient = new DefaultHttpClient();
		PushResult pushResult = null;
		try{
			String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
			msg.setPredefinedKeyValue("timestamp", timestamp);
			String url = HOST + POST_PATH;
			String postBody = msg.getPostBody();
			String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
			url = url + "?sign=" + sign;
			HttpPost post = new HttpPost(url);
			post.setHeader("User-Agent", USER_AGENT);
			StringEntity se = new StringEntity(postBody, "UTF-8");
			post.setEntity(se);
			/**
			 * Send the post request and get the response
			 */
			HttpResponse response = defaultHttpClient.execute(post);
			log.info("post body:{}>>>>>>>>>>>>>>>", postBody);
			log.info("post content:{}>>>>>>>>>>>>>", JSONObject.toJSON(post));
			int status = response.getStatusLine().getStatusCode();
			log.info("Response Code : " + status);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			log.info(result.toString());
			if (status == 200) {
				log.warn("消息内容调度成功，消息内容{}", postBody);
				pushResult = JSONObject.parseObject(result.toString(), PushResult.class);
				return pushResult;
			} else {
				log.error("Failed to send the notification!");
			}
		}catch (Exception e){
			log.error("push notification error!", e);
		}
		return pushResult;
	}


	/**
	 * Upload file with device_tokens to Umeng
	 * @param appkey
	 * @param appMasterSecret
	 * @param contents
	 * @return
	 * @throws Exception
	 */
	@Override
	public String uploadContents(String appkey,String appMasterSecret,String contents) throws Exception {
		HttpClient defaultHttpClient = new DefaultHttpClient();
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = HOST + UPLOAD_PATH;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		/**
		 * Send the post request and get the response
		 */
		HttpResponse response = defaultHttpClient.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		/**
		 * Decode response string and get file_id from it
		 */
		JSONObject respJson = JSONObject.parseObject(result.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		/**
		 * Set file_id into rootJson using setPredefinedKeyValue
		 */

		return fileId;
	}


	/**
	 * 查询task status
	 * @param appkey
	 * @param appMasterSecret
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public String obtainTaskStatus(String appkey,String appMasterSecret,String taskId) throws Exception {
		HttpClient defaultHttpClient = new DefaultHttpClient();
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("task_id", taskId);
		// Construct the request
		String url = HOST + TASK_STATUS;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		/**
		 * Send the post request and get the response
		 */
		HttpResponse response = defaultHttpClient.execute(post);
		log.info("Response Code:{}", response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		log.info("task status result:{}", result.toString());
		/**
		 * Decode response string and get file_id from it
		 */
		JSONObject respJson = JSONObject.parseObject(result.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		log.info("task status data:{}", data);
		return data.toJSONString();
	}
}
