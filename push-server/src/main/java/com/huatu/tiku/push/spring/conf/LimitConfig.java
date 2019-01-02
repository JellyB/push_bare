package com.huatu.tiku.push.spring.conf;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.huatu.tiku.push.constant.RabbitMqKey;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-01-02 下午4:56
 **/

@Component
@Order(value = Integer.MAX_VALUE - 199)
public class LimitConfig {


    @PostConstruct
    public void init() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(RabbitMqKey.NOTICE_USER_LANDING_HIKARICP_TEST);
        flowRule.setCount(20);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");
        rules.add(flowRule);

        FlowRuleManager.loadRules(rules);
    }
}
