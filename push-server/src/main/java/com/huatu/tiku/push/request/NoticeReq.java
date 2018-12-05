package com.huatu.tiku.push.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-11 下午9:08
 **/
@Getter
@Setter
@NoArgsConstructor
public class NoticeReq extends BaseReq{

    /**
     * 消息类型
     */
    private String type;

    /**
     * 具体消息类型
     */
    private String detailType;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 二级标题
     */
    private String subTitle;

    /**
     * 消息test
     */
    private String text;

    /**
     * 自定义消息内容
     */
    private Map<String,Object> custom;

    /**
     * 自定义消息展示类型
     */
    private Integer displayType;

    /**
     * 此消息系的用户
     */
    private List<NoticeUserRelation> users;

    @Builder
    public NoticeReq(String type, String detailType, String title, String subTitle, String text, Map<String, Object> custom, Integer displayType, List<NoticeUserRelation> users) {
        this.type = type;
        this.detailType = detailType;
        this.title = title;
        this.subTitle = subTitle;
        this.text = text;
        this.custom = custom;
        this.displayType = displayType;
        this.users = users;
    }




    @Getter
    @Setter
    @NoArgsConstructor
    public static class NoticeUserRelation extends BaseReq{

        private Long userId;

        private Long noticeId;

        private Date createTime;

        @Builder

        public NoticeUserRelation(Long userId, Long noticeId, Date createTime) {
            this.userId = userId;
            this.noticeId = noticeId;
            this.createTime = createTime;
        }
    }

}
