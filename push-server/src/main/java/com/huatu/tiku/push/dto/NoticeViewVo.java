package com.huatu.tiku.push.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-27 上午9:43
 **/

@NoArgsConstructor
@Getter
@Setter
public class NoticeViewVo {
    private String view;
    private int count;
    private String content;
    private String timeInfo;
    private String name;
    private long sortIndex;


    @Builder
    public NoticeViewVo(String view, int count, String content, String timeInfo, String name, long sortIndex) {
        this.view = view;
        this.count = count;
        this.content = content;
        this.timeInfo = timeInfo;
        this.name = name;
        this.sortIndex = sortIndex;
    }
}
