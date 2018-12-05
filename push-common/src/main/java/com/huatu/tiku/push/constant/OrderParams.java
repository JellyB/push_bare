package com.huatu.tiku.push.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-08 上午11:20
 **/
public class OrderParams {

    public static final String TYPE = "order";

    public static final String SEND = "send";

    public static final String SIGN = "sign";



    public static class Builder{
        private Map<String, String> params = new HashMap<>();

        public Map<String, String> getParams() {
            return params;
        }

        public static OrderParams.Builder builder(){
            return new OrderParams.Builder();
        }

        public OrderParams.Builder orderId(String orderId){
            this.params.put("orderId", orderId);
            return this;
        }

        public OrderParams.Builder orderTime(String orderTime){
            this.params.put("orderTime", orderTime);
            return this;
        }

        public OrderParams.Builder build(){
            return this;
        }
    }
}
