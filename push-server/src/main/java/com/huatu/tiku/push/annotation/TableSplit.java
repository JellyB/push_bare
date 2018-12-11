package com.huatu.tiku.push.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：分表注解
 * @author biguodong
 * Create time 2018-12-03 下午9:06
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableSplit {

    /**
     * 是否分表
     * @return
     */
    boolean split() default true;

    /**
     * 标示表名前缀
     * @return
     */
    String value();

    /**
     * 分表策略
     * @return
     */
    String strategy();
}
