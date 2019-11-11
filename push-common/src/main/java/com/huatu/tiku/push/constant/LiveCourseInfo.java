package com.huatu.tiku.push.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-11-11 3:13 PM
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveCourseInfo {

    private int afterExercisesNum;

    private int buildType;

    private long syllabusId;

    private int subjectType;
}
