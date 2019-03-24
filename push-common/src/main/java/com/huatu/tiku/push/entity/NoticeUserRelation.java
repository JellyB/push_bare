package com.huatu.tiku.push.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-06 下午4:18
 **/

@Data
@ToString
@Table(name = "r_notice_user")
@NoArgsConstructor
public class NoticeUserRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 具体消息类型
     */
    private String detailType;

    /**
     * 消息id
     */
    private Long noticeId;

    /**
     * 学员id
     */
    private Long userId;

    /**
     * 是否已读：0未读；1已读
     * {@link com.huatu.tiku.push.enums.NoticeReadEnum}
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新人
     */
    private Timestamp updateTime;

    /**
     * 数据状态 1 可用 0 删除
     */
    private Integer status;

    /**
     * 业务数据状态
     */
    private Integer bizStatus;


    @Builder
    public NoticeUserRelation(Long id, String type, String detailType, Long noticeId, Long userId, Integer isRead, Timestamp createTime, Timestamp updateTime, Integer status, Integer bizStatus) {
        this.id = id;
        this.type = type;
        this.detailType = detailType;
        this.noticeId = noticeId;
        this.userId = userId;
        this.isRead = isRead;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
        this.bizStatus = bizStatus;
    }
}
