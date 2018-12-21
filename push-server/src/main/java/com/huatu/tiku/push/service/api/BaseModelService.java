package com.huatu.tiku.push.service.api;

import com.huatu.tiku.push.request.BaseModel;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-21 下午3:12
 **/
public interface BaseModelService {

    /**
     * 取个消息
     * @param baseModel
     */
    void info(BaseModel baseModel);
}
