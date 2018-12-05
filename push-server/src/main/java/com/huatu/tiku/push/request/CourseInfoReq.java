package com.huatu.tiku.push.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-07 下午3:51
 **/


public class CourseInfoReq extends BaseReq{


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Batch{
        @NotEmpty(message = "直播课信息不能为空!")
        private List<Model> list;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Model{
        /**
         * 直播id
         */
        @NotNull(message = "直播ID不能为空")
        private String id;

        /**
         * 课程名称
         */
        @NotNull(message = "课程名称不能为空!")
        private String classTitle;

        /**
         * 课程id
         */
        @NotNull(message = "课程id不能为空！")
        private String classId;

        /**
         * 本节内容
         */
        @NotNull(message = "本节内容不能为空！")
        private String section;

        /**
         * 直播开始时间
         */
        @NotNull(message = "直播考试时间不能为空！")
        private String startTime;

        /**
         * 直播结束时间
         */
        @NotNull(message = "直播结束时间不能为空！")
        private String endTime;

        /**
         * 是否是直播
         */
        private String isLive;

        /**
         * 授课老师
         */
        @NotNull(message = "授课老师不能！")
        private String teacher;

        /**
         * 课程缩略图
         */
        @NotNull(message = "直播结束时间不能为空！")
        private String classImg;
    }

}
