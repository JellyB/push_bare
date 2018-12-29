package com.huatu.tiku.push.spring.aop;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.annotation.SplitParam;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.dao.strategy.Strategy;
import com.huatu.tiku.push.util.ConsoleContext;
import com.huatu.tiku.push.util.ThreadLocalManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-04 下午8:37
 **/

@Order(Ordered.HIGHEST_PRECEDENCE + 100)
@Component
@Aspect
@Slf4j
public class SplitParamAspect {

    @Pointcut(value = "@annotation(com.huatu.tiku.push.annotation.SplitParam)")
    public void pointCut(){

    }

    /**
     * 放入本地线程中
     * @param joinPoint
     */
    @Before("pointCut() && @annotation(splitParam)")
    public void before(JoinPoint joinPoint, SplitParam splitParam){
        Object[] argsObject = joinPoint.getArgs();
        if(argsObject.length == 0){
            throw new BizException(NoticePushErrors.TABLE_SPLIT_PARAMS_EMPTY);
        }
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        try{
            Object value = argsObject[splitParam.index()];
            if(!(value instanceof Number)){
                throw new BizException(NoticePushErrors.TABLE_SPLIT_PARAMS_TYPE_ERROR);
            }
            Long valueL = NumberUtils.convertNumberToTargetClass((Number) value, Long.class);
            ConsoleContext consoleContext = ConsoleContext.getInstance();
            Map<String, Object> params = Maps.newHashMap();
            params.put(Strategy.USER_ID, valueL);
            consoleContext.setRequestHeader(params);
            ThreadLocalManager.setConsoleContext(consoleContext);
        }catch (Exception e){
            log.error("pars split params value error, method:{}", method.getName());
        }
    }

    /**
     * 切面执行完后移除
     */
    @After("pointCut()")
    public void after(){
        ThreadLocalManager.clear();
    }

    @AfterThrowing(value = "pointCut()", throwing = "throwable")
    public void afterException(Throwable throwable){
        if(null != throwable && null != throwable.getCause()){
            log.error(" run aspect caught an error:{}", JSONObject.toJSONString(throwable.getCause()));
            log.error(" run aspect caught an error:{}", throwable.getCause().getMessage());
        }
        throw new BizException(NoticePushErrors.TABLE_SPLIT_PARAMS_AOP_ERROR);
    }
}
