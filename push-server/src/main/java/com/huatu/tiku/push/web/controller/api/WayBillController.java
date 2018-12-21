package com.huatu.tiku.push.web.controller.api;

import com.huatu.common.SuccessMessage;
import com.huatu.springboot.web.version.mapping.annotation.ApiVersion;
import com.huatu.tiku.push.request.WayBillReq;
import com.huatu.tiku.push.service.api.WayBillService;
import com.huatu.tiku.push.util.NoticeQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-20 下午5:36
 **/

@RestController
@RequestMapping("wayBill")
@ApiVersion(value = "v1")
@Slf4j
public class WayBillController {


    @PostMapping
    public Object newInfo(@RequestBody  WayBillReq.Model req){
        try{
            NoticeQueue.instance().produce(req);
            return SuccessMessage.create("操作成功！");
        }catch (InterruptedException e){
            log.error("put new notice caught an exception");
            return SuccessMessage.create("服务异常!");
        }
    }

    @PostMapping(value = "test")
    public Object test(){
        try{
            for(int i = 0; i < 100; i ++){
                WayBillReq.Model req = new WayBillReq.Model();
                req.setClassId("2341234");
                req.setType(1);
                req.setUserName("app_ztk802796288");
                req.setInfo("北京市回龙观新村");
                req.setWayBillNo("1234123414123");
                req.setCreateTime("12412341234");
                req.setClassName("2018万人模考第三季巴拉巴拉" + i);
                NoticeQueue.instance().produce(req);
            }
        }catch (InterruptedException e){
            log.error("interruption error!", e);
        }
        return SuccessMessage.create("测试用例");
    }

    @GetMapping(value = "count")
    public Object getCount(){
        return NoticeQueue.instance().size();
    }
}
