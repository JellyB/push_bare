package com.huatu.tiku.push.constant;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午10:34
 **/
public abstract class CorrectParams extends Params{
    public static final String TYPE = "correct";
    public static final String DEAL_DATE = "dealDate";
    /**
     * 人工批改试题或 paper name
     */
    public static final String CORRECT_TITLE = "correct_title";
    /**
     * 答题卡创建时间
     */
    public static final String SUBMIT_TIME = "submit_time";
}
