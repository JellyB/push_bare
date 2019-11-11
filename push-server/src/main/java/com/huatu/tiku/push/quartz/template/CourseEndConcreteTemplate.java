package com.huatu.tiku.push.quartz.template;

import com.google.common.collect.ImmutableList;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.UmengNotification;
import com.huatu.tiku.push.constant.CourseParams;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.JumpTargetEnum;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-11-07 9:25 PM
 **/
@Service
@Slf4j
public class CourseEndConcreteTemplate extends AbstractCourseTemplate {

    @Override
    protected int getUserCountInRedis() {
        return super.getUserCountInRedis();
    }

    /**
     * push logic for custom cast
     *
     * @param courseInfo
     * @param courseParams
     * @param noticeRelations
     * @return
     * @throws BizException
     */
    @Override
    protected List<NoticeReq> noticePush(CourseInfo courseInfo, CourseParams.Builder courseParams, List<NoticeReq.NoticeUserRelation> noticeRelations) throws BizException {
        return null;
    }

    /**
     * push logic for file cast
     *
     * @param courseInfo
     * @param courseParams
     * @return
     * @throws BizException
     */
    @Override
    protected NoticeReq noticePush(CourseInfo courseInfo, CourseParams.Builder courseParams) throws BizException {
        return null;
    }

    /**
     * insert logic for custom
     *
     * @param courseInfo
     * @param courseParams
     * @param noticeRelations
     * @return
     * @throws BizException
     */
    @Override
    protected List<NoticeReq> noticeInsert(CourseInfo courseInfo, CourseParams.Builder courseParams, List<NoticeReq.NoticeUserRelation> noticeRelations) throws BizException {
        return null;
    }

    /**
     * insert logic for file
     *
     * @param courseInfo
     * @param courseParams
     * @return
     * @throws BizException
     */
    @Override
    protected NoticeReq noticeInsert(CourseInfo courseInfo, CourseParams.Builder courseParams) throws BizException {
        return null;
    }

    /**
     * custom notification push list
     *
     * @param liveId
     * @param noticePushList
     * @param jumpTargetEnum
     * @return
     * @throws BizException
     */
    @Override
    protected ImmutableList<UmengNotification> customCastNotification(long liveId, List<NoticeReq> noticePushList, JumpTargetEnum jumpTargetEnum) throws BizException {
        return super.customCastNotification(liveId, noticePushList, jumpTargetEnum);
    }

    /**
     * file notification push -->
     *
     * @param classId
     * @param liveId
     * @param noticeReq
     * @param jumpTargetEnum
     * @return
     * @throws BizException
     */
    @Override
    protected List<UmengNotification> fileCastNotification(long classId, long liveId, NoticeReq noticeReq, JumpTargetEnum jumpTargetEnum) throws BizException {
        return super.fileCastNotification(classId, liveId, noticeReq, jumpTargetEnum);
    }
}
