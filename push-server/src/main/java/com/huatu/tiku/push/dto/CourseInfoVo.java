package com.huatu.tiku.push.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-20 下午5:13
 **/
@Data
@NoArgsConstructor
public class CourseInfoVo {

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
     * 授课老师
     */
    private String teacher;

    /**
     * 课程缩略图
     */
    private String classImg;

}
