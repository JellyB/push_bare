package com.huatu.tiku.push.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 下午8:58
 **/

@Data
@ToString
@Table(name = "t_user_entity")
@NoArgsConstructor
public class SimpleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String userName;

    private String userType;

    private Long bizId;

    @Builder
    public SimpleUser(Long id, Long userId, String userName, String userType, Long bizId) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.bizId = bizId;
    }
}
