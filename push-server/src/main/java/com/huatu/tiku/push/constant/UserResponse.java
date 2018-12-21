package com.huatu.tiku.push.constant;

import com.huatu.tiku.push.service.api.impl.UserInfoComponentImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-21 上午11:01
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse{
    private List<UserInfoComponentImpl.User> data;
    private String code;
}
