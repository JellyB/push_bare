package com.huatu.tiku.push.cast;

import com.huatu.common.exception.BizException;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 下午2:55
 **/
public interface FileUploadService{


    /**
     * 终端上传file_id
     * @param classId
     * @return
     * @throws BizException
     */
    FileUploadTerminal uploadTerminal(long classId)throws BizException;

}
