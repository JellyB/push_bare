package com.huatu.tiku.push.spring.aop;


import com.huatu.tiku.push.annotation.SplitParam;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-04 下午8:37
 **/

@Order(Ordered.HIGHEST_PRECEDENCE - 99)
@Component
@Aspect
@Slf4j
public class SplitParamAspect {

    @Pointcut(value = "@annotation(com.huatu.tiku.push.annotation.SplitParam)")
    public void pointCut(){

    }


    @Before("pointCut() && @annotation(splitParam)")
    public void before(JoinPoint joinPoint, SplitParam splitParam){
        log.info("》》》》》》》》》》》》");
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Class[] claszz = methodSignature.getParameterTypes();


    }

    @AfterReturning("pointCut()")
    public void after(){
        log.info("《《《《《《《《《《《");
    }

    @AfterThrowing(value = "pointCut()")
    public void afterException(){
        log.error(">>>>>>>>>>>>>");
    }
}
