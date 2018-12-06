package com.huatu.tiku.push.cast;


import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author biguodong
 *
 */
public abstract class AbstractIOSNotification extends UmengNotification {

	/**
	 * Keys can be set in the aps level
	 */
	protected static final HashSet<String> APS_KEYS = new HashSet<String>(Arrays.asList(new String[]{
			"alert", "badge", "sound", "content-available"
	}));

	protected static final HashSet<String> ALERT_kEYS = new HashSet<String>(Arrays.asList(new String[]{
			"title", "subtitle", "body"
	}));

	@Override
	public boolean setPredefinedKeyValue(String key, Object value) throws Exception {
		if (ROOT_KEYS.contains(key)) {
			/**
			 * This key should be in the root level
			 */
			rootJson.put(key, value);
		} else if (APS_KEYS.contains(key)) {
			/**
			 *  This key should be in the aps level
			 */
			JSONObject apsJson = null;
			JSONObject payloadJson = null;
			if (rootJson.containsKey("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload", payloadJson);
			}
			if (payloadJson.containsKey("aps")) {
				apsJson = payloadJson.getJSONObject("aps");
			} else {
				apsJson = new JSONObject();
				payloadJson.put("aps", apsJson);
			}
			apsJson.put(key, value);
		} else if (POLICY_KEYS.contains(key)) {
			// This key should be in the body level
			JSONObject policyJson = null;
			if (rootJson.containsKey("policy")) {
				policyJson = rootJson.getJSONObject("policy");
			} else {
				policyJson = new JSONObject();
				rootJson.put("policy", policyJson);
			}
			policyJson.put(key, value);
		} else {
			if (key == "payload" || key == "aps" || key == "policy") {
				throw new Exception("You don't need to set value for " + key + " , just set values for the sub keys in it.");
			} else {
				throw new Exception("Unknownd key: " + key);
			}
		}
		
		return true;
	}

	/**
	 * Set customized key/value for IOS notification
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public boolean setCustomizedField(String key, String value) throws Exception {
		/**
		 * rootJson.put(key, value);
		 */
		JSONObject payloadJson = null;
		if (rootJson.containsKey("payload")) {
			payloadJson = rootJson.getJSONObject("payload");
		} else {
			payloadJson = new JSONObject();
			rootJson.put("payload", payloadJson);
		}
		payloadJson.put(key, value);
		return true;
	}

	public boolean setAlertBody(String key, String value)throws Exception{
		JSONObject payloadJson = null;
		JSONObject apsJson = null;
		JSONObject alertJson = null;
		if (rootJson.containsKey("payload")) {
			payloadJson = rootJson.getJSONObject("payload");
		} else {
			payloadJson = new JSONObject();
			rootJson.put("payload", payloadJson);
		}
		if (payloadJson.containsKey("aps")) {
			apsJson = payloadJson.getJSONObject("aps");
		} else {
			apsJson = new JSONObject();
			payloadJson.put("aps", apsJson);
		}
		if(apsJson.containsKey("alert")){
			alertJson = apsJson.getJSONObject("alert");
		}else{
			alertJson = new JSONObject();
			apsJson.put("alert", alertJson);
		}
		if(ALERT_kEYS.contains(key)){
			alertJson.put(key, value);
		}
		return true;
	}

	@Deprecated
	public void setAlert(String token) throws Exception {
		setAlertBody("alert", token);
    }

    public void setAlertTitle(String token) throws Exception{
		setAlertBody("title", token);
	}
	public void setAlertSubtitle(String token) throws Exception{
		setAlertBody("subtitle", token);
	}
	public void setAlertBody(String token) throws Exception{
		setAlertBody("body", token);
	}
	
	public void setBadge(Integer badge) throws Exception {
    	setPredefinedKeyValue("badge", badge);
    }
	
	public void setSound(String sound) throws Exception {
    	setPredefinedKeyValue("sound", sound);
    }
	
	public void setContentAvailable(Integer contentAvailable) throws Exception {
    	setPredefinedKeyValue("content-available", contentAvailable);
    }
}
