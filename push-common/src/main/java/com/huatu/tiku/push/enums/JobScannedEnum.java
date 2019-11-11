package com.huatu.tiku.push.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： < 0 标示可以创建任务
 *
 * @author biguodong
 * Create time 2018-11-09 下午5:22
 **/
@AllArgsConstructor
@Getter
public enum  JobScannedEnum {


    NOT_YET_SCANNED(-2, "没有扫描过"),
    REMIND_JOB_HAS_CREATED_OR_OBSOLETE(-1, "已经创建了提醒任务或错过提醒任务时间"),
    READY_JOB_CREATED(0, "创建了准备好任务"),
    SCANNED_CAN_NOT_CREATE_JOB(1, "扫描过，但是不符合创建任务条件!"),
    JOB_WAITING(2, "课前任务等待执行"),
    PROCESS_AND_SUCCESS(4, "处理且成功"),
    PROCESS_AND_ERROR(5, "处理并且失败"),
    COURSE_WORK_JOB_CREATED(3, "课后作业任务创建就绪");


    private int value;
    private String title;

    public static JobScannedEnum create(int value){
        for(JobScannedEnum jobScannedEnum : values()){
            if(jobScannedEnum.getValue() == value) {
                return jobScannedEnum;
            }
        }
        return null;
    }
}
