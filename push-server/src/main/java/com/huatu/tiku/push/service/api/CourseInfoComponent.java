package com.huatu.tiku.push.service.api;

import com.huatu.tiku.push.constant.LiveCourseInfo;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-11-11 3:03 PM
 **/
public interface CourseInfoComponent {

    LiveCourseInfo fetchCourseInfo(Long netClassId, Long liveCourseWareId);
}
