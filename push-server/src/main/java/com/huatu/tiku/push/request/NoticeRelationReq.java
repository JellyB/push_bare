package com.huatu.tiku.push.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-11 下午10:40
 **/
@Data
@NoArgsConstructor
public class NoticeRelationReq extends BaseReq{

    private Long noticeId;

    private Set<Long> users;

    @Builder
    public NoticeRelationReq(Long noticeId, Set<Long> users) {
        this.noticeId = noticeId;
        this.users = users;
    }
}
