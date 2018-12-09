package com.huatu.tiku.push.service.feign;

import com.huatu.tiku.push.response.DataMigrateResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 下午5:21
 **/
@FeignClient(value = "t-pandora-server", path = "/pand")
public interface DataMigrateFeign {

    /**
     * 数据迁移
     * @param params
     * @return
     */
    @GetMapping(value = "/migrate")
    DataMigrateResp migrate(@RequestParam Map<String, Object> params);
}
