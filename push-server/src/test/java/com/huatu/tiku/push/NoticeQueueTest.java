package com.huatu.tiku.push;

import com.huatu.tiku.push.request.WayBillReq;
import com.huatu.tiku.push.util.NoticeQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-21 下午4:15
 **/
@Slf4j
public class NoticeQueueTest extends PushBaseTest{



    @Test
    public void batch(){
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
    }

}
