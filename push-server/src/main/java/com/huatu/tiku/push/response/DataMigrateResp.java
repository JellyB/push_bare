package com.huatu.tiku.push.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 下午5:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataMigrateResp{

    private InnerData data;

    private String code;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerData{
        private int total;
        private int next;
        private int totalPage;
        private List<Object> list;
    }
}
