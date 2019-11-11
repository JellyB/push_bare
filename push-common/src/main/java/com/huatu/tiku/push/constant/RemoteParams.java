package com.huatu.tiku.push.constant;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-11-07 9:06 PM
 **/
public class RemoteParams extends Params {

    public static final String TYPE = "remote";

    public static final String DETAIL_TYPE = "alert";
    /**
     * notice type
     *
     * @return
     */
    @Override
    public String getType() {
        return TYPE;
    }
}
