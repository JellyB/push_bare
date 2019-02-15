package com.huatu.tiku.push.dingtalk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-23 下午3:33
 **/
@Data
@NoArgsConstructor
public class TextMsg extends Msg{

    private JSONObject text;

    private JSONObject at;


    @Builder
    public TextMsg(String content, boolean atAll, String  mobiles) {
        this.msgtype = "text";
        this.text = new JSONObject();
        this.text.put("content", content);
        this.at = new JSONObject();
        if(StringUtils.isNotEmpty(mobiles)){
            String [] mobiles_ = mobiles.split(",");
            JSONArray jsonArray = new JSONArray();
            for (String s : mobiles_) {
                jsonArray.add(s);
            }
            at.put("atMobiles", jsonArray);
        }
        this.at.put("isAtAll", atAll);
    }

    public void setAt(String mobiles) {
        if(StringUtils.isNotEmpty(mobiles)){
            String [] mobiles_ = mobiles.split(",");
            JSONArray jsonArray = new JSONArray();
            for (String s : mobiles_) {
                jsonArray.add(s);
            }
            at.put("atMobiles", jsonArray);
        }
    }
}
