package com.huatu.tiku.push.service.api;


import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.request.CourseInfoReq;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-07 下午3:42
 **/

public interface CourseService {

    /**
     * 新增直播课信息到本地库
     * @param req
     * @return
     * @throws BizException
     */
    Object saveCourseInfo(CourseInfoReq.Model req) throws BizException;

    /**
     * 批量新增直播信息
     * @param batch
     * @return
     * @throws BizException
     */
    Object saveCourseInfoBatch(CourseInfoReq.Batch batch) throws BizException;

    /**
     * 逻辑删除直播课
     * @param liveId
     * @return
     * @throws BizException
     */
    Object removeCourseInfo(long liveId) throws BizException;


    /**
     * 分页查询添加的推送课程list
     * 创建时间倒排
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    Object list(int page, int size) throws BizException;
}
