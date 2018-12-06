package com.huatu.tiku.push.dingtalk;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-23 下午4:36
 **/
@Data
@NoArgsConstructor
public class LinkMsg extends Msg{
    private String title;
    private JSONObject link;

    @Builder
    public LinkMsg(String title, String text, String messageUrl, String picUrl) {
        this.msgtype = "link";
        this.link = new JSONObject();
        this.link.put("title", title);
        this.link.put("text", text);
        this.link.put("messageUrl", messageUrl);
        this.link.put("picUrl", picUrl);
    }
}
