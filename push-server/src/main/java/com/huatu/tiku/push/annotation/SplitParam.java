package com.huatu.tiku.push.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：有此注解的方法参与数据库分表
 *
 * @author biguodong
 * Create time 2018-12-04 下午8:34
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SplitParam {

    /**
     *
     * 使用参数列表的索引
     * @return
     */
    int index() default 0;
}
