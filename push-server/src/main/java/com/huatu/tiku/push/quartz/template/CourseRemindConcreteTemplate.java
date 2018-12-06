package com.huatu.tiku.push.quartz.template;

import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.CourseParams;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.quartz.factory.CourseCastFactory;
import com.huatu.tiku.push.request.NoticeReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-21 下午9:26
 **/
@Slf4j
@Service
public class CourseRemindConcreteTemplate extends AbstractCourseTemplate{


    /**
     * push logic
     *
     * @param courseInfo
     * @param courseParams
     * @param noticeRelations
     * @return
     * @throws BizException
     */
    @Override
    protected List<NoticeReq> noticePush(CourseInfo courseInfo, CourseParams.Builder courseParams, List<NoticeReq.NoticeUserRelation> noticeRelations) throws BizException {
        return  CourseCastFactory.noticeRemindPush(courseInfo, courseParams, noticeRelations);

    }

    /**
     * insert logic
     *
     * @param courseInfo
     * @param courseParams
     * @param noticeRelations
     * @return
     * @throws BizException
     */
    @Override
    protected List<NoticeReq> noticeInsert(CourseInfo courseInfo, CourseParams.Builder courseParams, List<NoticeReq.NoticeUserRelation> noticeRelations) throws BizException {
        return CourseCastFactory.noticeRemindInsert(courseInfo, courseParams, noticeRelations);
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
        return CourseCastFactory.noticeRemindPush(courseInfo, courseParams);
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
}
