package com.huatu.tiku.push.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：运单编号
 *
 * @author biguodong
 * Create time 2018-12-20 下午5:47
 **/

public class WayBillReq extends BaseReq{


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Model extends BaseModel{

        /**
         * 课程id
         */
        private String classId;

        /**
         * 课程name
         */
        private String className;

        /**
         * 0 发货
         * 1 签收
         */
        private int type;

        /**
         * 用户昵称
         */
        private String userName;

        /**
         * 物流信息
         */
        private String info;

        /**
         * 运单编号
         */
        private String wayBillNo;

        /**
         * 订单时间
         */
        private String createTime;
    }
}
