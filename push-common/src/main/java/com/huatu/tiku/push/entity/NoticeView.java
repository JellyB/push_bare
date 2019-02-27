package com.huatu.tiku.push.entity;

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
 * Create time 2019-02-27 上午11:11
 **/

@Data
@ToString
@NoArgsConstructor
@Table(name = "t_notice_view")
public class NoticeView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 最新一条消息id
     */
    private Long noticeId;

    /**
     * 消息类型
     */
    private String view;

    /**
     * 我读消息数量
     */
    private Integer count;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

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
}
