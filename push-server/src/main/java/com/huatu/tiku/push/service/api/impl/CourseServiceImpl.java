package com.huatu.tiku.push.service.api.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.huatu.common.ErrorResult;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.dao.CourseInfoMapper;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.enums.JobScannedEnum;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import com.huatu.tiku.push.request.CourseInfoReq;
import com.huatu.tiku.push.response.CourseInfoResp;
import com.huatu.tiku.push.service.api.CourseService;
import com.huatu.tiku.push.service.api.QuartzJobInfoService;
import com.huatu.tiku.push.util.NoticeTimeParseUtil;
import com.huatu.tiku.push.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-07 下午3:43
 **/

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private static final int PHP_TIME_LENGTH = 10;
    private static final String PHP_JAVA_TIME = "000";

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    private QuartzJobInfoService quartzJobInfoService;

    /**
     * 新增直播课信息到本地库
     *
     * @param req
     * @return
     */
    @Override
    public Object saveCourseInfo(CourseInfoReq.Model req) throws BizException{
        CourseInfo courseInfo = getCourseInfo(req);
        if(checkLiveExist(courseInfo.getLiveId())){
            return updateCourseInfo(courseInfo);
        }else{
            return courseInfoMapper.insert(courseInfo);
        }
    }


    /**
     * 更新直播课信息
     * @param courseInfo
     * @return
     */
    public int updateCourseInfo(CourseInfo courseInfo){
        Example example = new Example(CourseInfo.class);
        example.and()
                .andEqualTo("liveId", courseInfo.getLiveId())
                .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue());
        CourseInfo courseInfo_ = new CourseInfo();
        BeanUtils.copyProperties(courseInfo, courseInfo_);
        courseInfo_.setId(null);
        courseInfo_.setStatus(null);
        courseInfo_.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return courseInfoMapper.updateByExampleSelective(courseInfo, example);
    }


    /**
     * 批量新增直播信息
     *
     * @param batch
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public Object saveCourseInfoBatch(CourseInfoReq.Batch batch) throws BizException{
        if(CollectionUtils.isEmpty(batch.getList())){
            throw new BizException(ErrorResult.create(1000010, "直播列表不能为空！"));
        }

        List<CourseInfo> list = batch.getList().stream()
                .map(CourseServiceImpl::getCourseInfo)
                .collect(Collectors.toList());


        List<CourseInfo> existCourseInfo = list.stream().filter(item-> checkLiveExist(Long.valueOf(item.getLiveId())))
                .collect(Collectors.toList());
        /**
         * 批量更新操作
         */
        if(CollectionUtils.isEmpty(existCourseInfo)){
            list.forEach(info -> courseInfoMapper.insert(info));

        }else{
            existCourseInfo.forEach(item-> updateCourseInfo(item));
        }
        return list.size();
    }

    /**
     * 分页查询添加的推送课程list
     * 创建时间倒排
     *
     * @param page
     * @param size
     * @return
     * @throws BizException
     */
    @Override
    public Object list(int page, int size) throws BizException {
        Example example = new Example(CourseInfo.class);
        example.orderBy("startTime").desc();

        PageInfo pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> courseInfoMapper.selectByExample(example));
        if(!CollectionUtils.isEmpty(pageInfo.getList())){
            List<CourseInfoResp> courseInfoResps = Lists.newArrayList();
            buildCourseResp(pageInfo.getList(), courseInfoResps);
            pageInfo.setList(courseInfoResps);
        }

        return PageUtil.parsePageInfo(pageInfo);
    }

    /**
     * 逻辑删除直播课
     *
     * @param liveId
     * @return
     * @throws BizException
     */
    @Override
    @Transactional
    public Object removeCourseInfo(long liveId) throws BizException {
        if(!checkLiveExist(liveId)){
            throw new BizException(NoticePushErrors.COURSE_INFO_UN_EXIST);
        }else{
            Example example = new Example(CourseInfo.class);
            example.and()
                    .andEqualTo("liveId", liveId)
                    .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue());
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setStatus(NoticeStatusEnum.DELETE_LOGIC.getValue());
            quartzJobInfoService.deleteJobByBizData(String.valueOf(liveId));
            return courseInfoMapper.updateByExampleSelective(courseInfo, example);

        }
    }

    /**
     * check live exist
     * @param liveId
     * @return
     */
    public boolean checkLiveExist(long liveId){
        Example example = new Example(CourseInfo.class);
        example.and()
                .andEqualTo("liveId", liveId)
                .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue());
        List<CourseInfo> list = courseInfoMapper.selectByExample(example);
        return !CollectionUtils.isEmpty(list);
    }


    /**
     *
     * @param courseInfos
     * @param courseInfoResps
     * @throws BizException
     */
    private void buildCourseResp(List<CourseInfo> courseInfos, List<CourseInfoResp> courseInfoResps)throws BizException{
        courseInfos.forEach(item -> {
            CourseInfoResp courseInfoResp = new CourseInfoResp();
            BeanUtils.copyProperties(item, courseInfoResp);
            courseInfoResp.setStartDate(NoticeTimeParseUtil.localDateFormat.format(item.getStartTime()));
            courseInfoResp.setEndDate(NoticeTimeParseUtil.localDateFormat.format(item.getEndTime()));
            courseInfoResp.setCreateDate(NoticeTimeParseUtil.localDateFormat.format(item.getCreateTime()));
            courseInfoResp.setUpdateDate(NoticeTimeParseUtil.localDateFormat.format(item.getUpdateTime()));
            courseInfoResps.add(courseInfoResp);
        });
    }
    /**
     * model --> courseInfo
     * @param req
     * @return
     */
    private static CourseInfo getCourseInfo(CourseInfoReq.Model req) throws BizException{
        CourseInfo courseInfo = new CourseInfo();
        formatStartTime(req);
        courseInfo.setClassTitle(req.getClassTitle());
        courseInfo.setClassId(Long.valueOf(req.getClassId()));
        courseInfo.setSection(req.getSection());
        courseInfo.setStartTime(new Timestamp(Long.valueOf(req.getStartTime())));
        parseCourseStartTimeIllegal(courseInfo.getStartTime().getTime());
        courseInfo.setEndTime(new Timestamp(Long.valueOf(req.getEndTime())));
        courseInfo.setIsLive(req.getIsLive());
        courseInfo.setTeacher(req.getTeacher());
        courseInfo.setClassImg(req.getClassImg());
        courseInfo.setLiveId(Long.valueOf(req.getId()));
        courseInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        courseInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        courseInfo.setCreator(0L);
        courseInfo.setModifier(0L);
        courseInfo.setBizStatus(1);
        courseInfo.setStatus(NoticeStatusEnum.NORMAL.getValue());
        JobScannedEnum jobScannedEnum = NoticeTimeParseUtil.obtainScannedEnumWhenCourseCreateOrUpdate(courseInfo.getStartTime().getTime());
        courseInfo.setScanned(jobScannedEnum.getValue());
        return courseInfo;
    }

    /**
     * 格式化php过来的时间格式
     * @param req
     */
    private static void formatStartTime(CourseInfoReq.Model req){

        if(req.getStartTime().length() == PHP_TIME_LENGTH){
            req.setStartTime(req.getStartTime() + PHP_JAVA_TIME);
        }
        if(req.getEndTime().length() == PHP_TIME_LENGTH){
            req.setEndTime(req.getEndTime() + PHP_JAVA_TIME);
        }
    }


    /**
     * 校验课程开课时间是否合法
     *
     * @param startTime
     */
    private static void parseCourseStartTimeIllegal(long startTime)throws BizException{
        final long currentTime = System.currentTimeMillis();
        long difference = NoticeTimeParseUtil.COURSE_READY_MINIMUM_DATE
                + NoticeTimeParseUtil.DEVIATION_DATE_TIME
                + NoticeTimeParseUtil.COURSE_USER_FETCH_INTERVAL;

        if(startTime - currentTime < difference){
            throw new BizException(NoticePushErrors.COURSE_START_TIME_ILLEGAL);
        }
    }
}
