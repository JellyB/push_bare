package com.huatu.tiku.push.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-18 下午10:00
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T>{

    private List<T> data;
    private long total;
    private int page;
    private int size;
}
