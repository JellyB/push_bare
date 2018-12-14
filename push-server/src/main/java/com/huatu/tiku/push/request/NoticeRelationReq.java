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

    private String type;

    private String detailType;

    private Set<Long> users;

    @Builder
    public NoticeRelationReq(Long noticeId, String type, String detailType, Set<Long> users) {
        this.noticeId = noticeId;
        this.type = type;
        this.detailType = detailType;
        this.users = users;
    }
}
