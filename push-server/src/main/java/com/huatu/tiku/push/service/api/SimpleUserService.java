package com.huatu.tiku.push.service.api;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.FileUploadTerminal;
import com.huatu.tiku.push.entity.SimpleUser;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-13 下午9:15
 **/
public interface SimpleUserService {

    /**
     * 入库
     * @param simpleUserList
     * @param classId
     * @param liveId
     * @param endTime
     * @throws BizException
     */
    void storeSimpleUser(long classId, long liveId, List<SimpleUser> simpleUserList, long endTime) throws BizException;

    /**
     * 是否需要文件上传
     * @param classId
     * @param liveId
     * @throws BizException
     */
    void judgeFileUpload(long classId, long liveId)throws BizException;

    /**
     * 获取文件上传id(redis)
     * @param classId
     * @param liveId
     * @return
     * @throws BizException
     */
    FileUploadTerminal obtainFileUpload(long classId, long liveId)throws BizException;
}
