package com.huatu.tiku.push.response;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-29 下午1:20
 **/
@Data
@NoArgsConstructor
public class CourseInfoResp extends BaseResp {

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
    private String startDate;

    /**
     * 直播结束时间
     */
    private String endDate;

    /**
     * 授课老师
     */
    private String teacher;

    /**
     * 课程缩略图
     */
    private String classImg;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 更新时间
     */
    private String updateDate;
}
