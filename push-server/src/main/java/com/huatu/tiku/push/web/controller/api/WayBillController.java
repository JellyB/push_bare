package com.huatu.tiku.push.web.controller.api;

import com.huatu.springboot.web.version.mapping.annotation.ApiVersion;
import com.huatu.tiku.push.request.WayBillReq;
import com.huatu.tiku.push.service.api.WayBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-20 下午5:36
 **/

@RestController
@RequestMapping("wayBill")
@ApiVersion(value = "v1")
public class WayBillController {


    @Autowired
    private WayBillService wayBillService;


    @PostMapping
    public Object newInfo(WayBillReq.Model req){
        return wayBillService.info(req);
    }
}
