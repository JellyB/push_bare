package com.huatu.tiku.push.entity;

import lombok.Builder;
import lombok.Data;
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
 * Create time 2018-11-06 下午4:16
 **/

@Data
@ToString
@Table(name = "t_notice_entity")
public class NoticeEntity {

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
     * 消息标题
     */
    private String title;

    /**
     * 消息test
     */
    private String text;

    /**
     * 自定义消息内容
     */
    private String custom;

    /**
     * 自定义消息展示类型
     */
    private Integer displayType;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 更新人
     */
    private Long modifier;

    /**
     * 数据状态 1 可用 0 删除
     */
    private Integer status;

    /**
     * 业务数据状态
     */
    private Integer bizStatus;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    @Builder
    public NoticeEntity(Long id, String type, String detailType, String title, String text, String custom, Integer displayType, Long creator, Long modifier, Integer status, Integer bizStatus, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.type = type;
        this.detailType = detailType;
        this.title = title;
        this.text = text;
        this.custom = custom;
        this.displayType = displayType;
        this.creator = creator;
        this.modifier = modifier;
        this.status = status;
        this.bizStatus = bizStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
