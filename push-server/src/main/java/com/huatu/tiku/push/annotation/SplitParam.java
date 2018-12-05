package com.huatu.tiku.push.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-04 下午8:34
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SplitParam {

    /**
     *
     * 分表字段数组
     * @return
     */
    String splitParams() default "";
}
