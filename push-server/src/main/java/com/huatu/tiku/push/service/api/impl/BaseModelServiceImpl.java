package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.request.BaseModel;
import com.huatu.tiku.push.request.WayBillReq;
import com.huatu.tiku.push.service.api.BaseModelService;
import com.huatu.tiku.push.service.api.WayBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-21 下午3:14
 **/
@Service
@Slf4j
public class BaseModelServiceImpl implements BaseModelService {
    @Autowired
    private WayBillService wayBillService;

    @Override
    public void info(BaseModel baseModel) {
        log.error("开始执行BaseModelServiceImpl info 方法 >>>>>>>>>>>");
        if(baseModel instanceof WayBillReq.Model){
            log.error("是的");
            WayBillReq.Model req = (WayBillReq.Model) baseModel;
            wayBillService.info(req);
        }else{
            log.error("不能处理的model类型:{}", JSONObject.toJSONString(baseModel));
        }
    }
}
