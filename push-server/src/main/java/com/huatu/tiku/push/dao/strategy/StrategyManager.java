package com.huatu.tiku.push.dao.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：分表策略管理器
 * @author biguodong
 * Create time 2018-12-03 下午8:56
 **/
@Component
@Slf4j
public class StrategyManager {

    private Map<String, Strategy> strategies = new ConcurrentHashMap<String,Strategy>(10);

    public Strategy getStrategy(String key){
        return strategies.get(key);
    }

    private Map<String, Strategy> getStrategies(){
        return strategies;
    }

    @Resource
    private MoldStrategy moldStrategy;

    @PostConstruct
    public void init(){
        this.strategies.put(Strategy.MOLD_STRATEGY, moldStrategy);
    }
    /**
     * set strategy
     * @param strategies
     */
    public void setStrategies(Map<String,String> strategies){
        for(Map.Entry<String,String> entry: strategies.entrySet()){
            try{
               this.strategies.put(entry.getKey(), (Strategy)Class.forName(entry.getValue()).newInstance());
            }catch (Exception e){
                log.error("实例化策略失败！！", e);
            }
        }
        printDebugInfo();
    }

    private void printDebugInfo(){
        if(log.isDebugEnabled()){
            StringBuffer stringBuffer = new StringBuffer("初始化" + strategies.size() + "策略！");
            for(String key : strategies.keySet()){
                stringBuffer.append("\n")
                        .append(key)
                        .append(" ----> ")
                        .append(strategies.get(key));
            }
            log.debug(stringBuffer.toString());
        }
    }
}
