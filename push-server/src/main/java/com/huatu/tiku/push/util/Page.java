package com.huatu.tiku.push.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-07 下午7:00
 **/
@Getter
@Setter
@NoArgsConstructor
public class Page<E> implements Serializable {
    private long total;
    private Integer next;
    private int totalPage;
    private List<E> list;

    public Page(long total, Integer next, int totalPage) {
        this.total = total;
        this.next = next;
        this.totalPage = totalPage;
    }
}
