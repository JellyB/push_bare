package com.huatu.tiku.push.interceptor;

import com.google.common.collect.Maps;
import com.huatu.tiku.push.annotation.TableSplit;
import com.huatu.tiku.push.dao.strategy.Strategy;
import com.huatu.tiku.push.dao.strategy.StrategyManager;
import com.huatu.tiku.push.util.ContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * 描述：mybatis 自定义拦截器实现数据库分表逻辑
 * @author biguodong
 * Create time 2018-12-03 下午9:15
 **/
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class TableSplitInterceptor implements Interceptor {

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_OBJECT_REFLECTOR_FACTORY = new DefaultReflectorFactory();


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_OBJECT_REFLECTOR_FACTORY);
        doSplitTable(metaStatementHandler);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        /**
         * 当目标是StatementHandler类型时，才包装目标
         * 否则直接返回目标本身，减少目标被代理次数
         */
        if(target instanceof StatementHandler){
            return Plugin.wrap(target, this);
        }else{
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void doSplitTable(MetaObject metaStatementHandler) throws ClassNotFoundException{
        String originSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        if(StringUtils.isNotBlank(originSql)){
            MappedStatement  mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            Class<?> classObj = Class.forName(className);
            /**
             * 根据配置自动生成SQL
             */
            TableSplit tableSplit = classObj.getAnnotation(TableSplit.class);
            if(null != tableSplit && tableSplit.split()){
                if(tableSplit.value().equalsIgnoreCase(Strategy.NOTICE_RELATION)){
                    log.info("关系表-分表前语句:{}", originSql);
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(Strategy.TABLE_NAME, tableSplit.value());
                    StrategyManager strategyManager = ContextHelper.getBean(StrategyManager.class);
                    String strategyName = tableSplit.strategy();
                    Strategy strategy = strategyManager.getStrategy(strategyName);
                    String tableName = strategy.convert(params);
                    String convertedSql = originSql.replaceAll(tableSplit.value(), tableName);
                    metaStatementHandler.setValue("delegate.boundSql.sql", convertedSql);
                    log.info("关系表-分表后语句:{}", convertedSql);
                }
            }
        }
    }
}
