package com.huatu.tiku.push.entity;

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
 * Create time 2018-11-07 下午3:45
 **/

@Data
@ToString
@Table(name = "t_notice_course")
public class CourseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 直播id
     */
    private Long liveId;

    /**
     * 课程名称
     */
    private String classTitle;

    /**
     * 课程id
     */
    private Long classId;

    /**
     * 本节内容
     */
    private String section;

    /**
     * 直播开始时间
     */
    private Timestamp startTime;

    /**
     * 直播结束时间
     */
    private Timestamp endTime;

    /**
     * 是否是直播
     */
    private String isLive;

    /**
     * 授课老师
     */
    private String teacher;

    /**
     * 课程缩略图
     */
    private String classImg;

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
     * 是否扫描过，如果扫描过说明有job
     */
    private Integer scanned;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
