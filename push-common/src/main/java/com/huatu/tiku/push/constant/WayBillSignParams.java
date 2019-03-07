package com.huatu.tiku.push.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-20 下午6:18
 **/

@Getter
@Setter
@NoArgsConstructor
public class WayBillSignParams extends WayBillParams{

    public static final String DETAIL_TYPE = "sign";

    /**
     * notice type
     *
     * @return
     */
    @Override
    public String getType() {
        return DETAIL_TYPE;
    }


    public static class Builder extends AbstractBuilder {

        /**
         * get params
         *
         * @return
         */
        @Override
        public Map<String, Object> getParams() {
            return params;
        }

        public static WayBillSignParams.Builder builder(){
            return new WayBillSignParams.Builder();
        }

        public WayBillSignParams.Builder bizId(String bizId){
            this.params.put(BIZ_ID, bizId);
            return this;
        }

        public WayBillSignParams.Builder orderId(String order){
            this.params.put("order", order);
            return this;
        }

        public WayBillSignParams.Builder orderTime(String orderTime){
            this.params.put("orderTime", orderTime);
            return this;
        }

        public WayBillSignParams.Builder build(){
            return this;
        }
    }
}
