package com.huatu.tiku.push.cast;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author biguodong
 */

public abstract class UmengNotification implements Serializable{

	@Value("${notice.push.env}")
	private String noticePushEnv;
	/**
	 * This JSONObject is used for constructing the whole request string.
	 */
	protected final JSONObject rootJson = new JSONObject();

	public boolean isProduct(){
		return noticePushEnv.equals(PRODUCT_MODE);
	}
	/**
	 * 生产模式
	 */
	protected final String PRODUCT_MODE = "product";
	/**
	 * FILE CAST
	 */
	protected String FILE_CAST = "filecast";

	/**
	 * CUSTOM CAST
	 */
	protected String CUSTOM_CAST = "customizedcast";

	/**
	 * alias type
	 */
	public static final String ALIAS_TYPE = "personID";

	/**
	 * The app master secret
	 */
	protected String appMasterSecret;

	/**
	 *  Keys can be set in the root level
	 */
	protected static final HashSet<String> ROOT_KEYS = new HashSet<String>(Arrays.asList(new String[]{
			"appkey", "timestamp", "type", "device_tokens", "alias", "alias_type", "file_id", 
			"filter", "production_mode", "feedback", "description", "thirdparty_id"}));

	/**
	 * Keys can be set in the policy level
	 */
	protected static final HashSet<String> POLICY_KEYS = new HashSet<String>(Arrays.asList(new String[]{
			"start_time", "expire_time", "max_send_num"
	}));
	
	/**
	 * Set predefined keys in the rootJson, for extra keys(Android) or customized keys(IOS) please
	 * refer to corresponding methods in the subclass.
	 */

	/**
	 * 设置预定义key value
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public abstract boolean setPredefinedKeyValue(String key, Object value) throws Exception;
	public void setAppMasterSecret(String secret) {
		appMasterSecret = secret;
	}
	
	public String getPostBody(){
		return rootJson.toString();
	}
	
	protected final String getAppMasterSecret(){
		return appMasterSecret;
	}

	/**
	 * 废弃
	 * @param prod
	 * @throws Exception
	 */
	@Deprecated
	protected void setProductionMode(Boolean prod) throws Exception {
    	setPredefinedKeyValue("production_mode", prod.toString());
    }

	/**
	 * 正式模式
	 * @throws Exception
	 */
	@Deprecated
    public void setProductionMode() throws Exception {
    	setProductionMode(true);
    }

	/**
	 * 测试模式
	 * @throws Exception
	 */
	@Deprecated
	public void setTestMode() throws Exception {
    	setProductionMode(false);
    }

	/**
	 * 发送消息描述，建议填写。
	 * @param description
	 * @throws Exception
	 */
	public void setDescription(String description) throws Exception {
    	setPredefinedKeyValue("description", description);
    }

	/**
	 * 定时发送时间，若不填写表示立即发送。格式: "YYYY-MM-DD hh:mm:ss"。
	 * @param startTime
	 * @throws Exception
	 */
	public void setStartTime(String startTime) throws Exception {
    	setPredefinedKeyValue("start_time", startTime);
    }

	/**
	 * 消息过期时间,格式: "YYYY-MM-DD hh:mm:ss"。
	 * @param expireTime
	 * @throws Exception
	 */
    public void setExpireTime(String expireTime) throws Exception {
    	setPredefinedKeyValue("expire_time", expireTime);
    }

	/**
	 * 发送限速，每秒发送的最大条数。
	 * @param num
	 * @throws Exception
	 */
    public void setMaxSendNum(Integer num) throws Exception {
    	setPredefinedKeyValue("max_send_num", num);
    }

}
