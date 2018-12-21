package com.huatu.tiku.push.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-20 下午6:17
 **/

@Getter
@Setter
@NoArgsConstructor
public class WayBillSendParams extends WayBillParams {


    public static final String DETAIL_TYPE = "send";

    /**
     * notice type
     *
     * @return
     */
    @Override
    public String getType() {
        return DETAIL_TYPE;
    }


    public static class Builder extends AbstractBuilder{

        /**
         * get params
         *
         * @return
         */
        @Override
        public Map<String, Object> getParams() {
            return params;
        }

        public static WayBillSendParams.Builder builder(){
            return new WayBillSendParams.Builder();
        }

        public WayBillSendParams.Builder bizId(String bizId){
            this.params.put(BIZ_ID, bizId);
            return this;
        }
        public WayBillSendParams.Builder orderId(String orderId){
            this.params.put("orderId", orderId);
            return this;
        }

        public WayBillSendParams.Builder orderTime(String orderTime){
            this.params.put("orderTime", orderTime);
            return this;
        }

        public WayBillSendParams.Builder build(){
            return this;
        }
    }
}
