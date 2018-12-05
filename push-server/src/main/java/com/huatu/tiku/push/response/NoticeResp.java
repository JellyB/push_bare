package com.huatu.tiku.push.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huatu.tiku.push.constant.BaseMsg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-06 下午4:24
 **/

@Getter
@Setter
@NoArgsConstructor
public class NoticeResp extends BaseResp{

    /**
     * 消息id
     */
    private Long noticeId;

    /**
     * 消息内容
     */
    private BaseMsg payload;

    /**
     * 消息类型 0 默认；1直播课；2模考大赛；3物流；4纠错反馈
     */
    private String type;

    /**
     * 具体类型，比如模考大赛有上线online，In5min，report
     */
    private String detailType;

    /**
     * 展示类型 1 有缩略图 0 没有缩略图
     */
    private Integer display_type;

    /**
     * 学员id
     */
    private Long userId;

    /**
     * 是否已读：0未读；1已读
     */
    private Integer isRead;

    /**
     * 消息时间
     */
    @JsonProperty
    private String noticeTime;

    @Builder

    public NoticeResp(Long noticeId, BaseMsg payload, String type, String detailType, Integer display_type, Long userId, Integer isRead, String noticeTime) {
        this.noticeId = noticeId;
        this.payload = payload;
        this.type = type;
        this.detailType = detailType;
        this.display_type = display_type;
        this.userId = userId;
        this.isRead = isRead;
        this.noticeTime = noticeTime;
    }
}
