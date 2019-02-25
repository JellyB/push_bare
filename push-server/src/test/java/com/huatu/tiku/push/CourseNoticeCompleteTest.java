package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.huatu.tiku.push.constant.CourseParams;
import com.huatu.tiku.push.constant.Params;
import com.huatu.tiku.push.dao.CourseInfoMapper;
import com.huatu.tiku.push.dao.NoticeEntityMapper;
import com.huatu.tiku.push.entity.CourseInfo;
import com.huatu.tiku.push.entity.NoticeEntity;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-02-24 下午9:50
 **/
@Slf4j
public class CourseNoticeCompleteTest extends PushBaseTest {

    private static final Map<String, CourseInfo> courseInfoHashMap = Maps.newHashMap();
    private static SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    private NoticeEntityMapper noticeEntityMapper;

    @Test
    public void completeTest(){
        Example courseExample = new Example(CourseInfo.class);
        courseExample.orderBy("id").asc();
        List<CourseInfo> courseInfos = courseInfoMapper.selectAll();
        log.error("courseInfo size from database was:{}", courseInfos.size());
        courseInfos.forEach(courseInfo -> {
            String key = courseInfo.getClassId() + courseInfo.getTeacher() + simpleDate.format(courseInfo.getStartTime());
            courseInfoHashMap.put(key, courseInfo);
        });
        log.error("courseInfoHashMap size was:{}", courseInfoHashMap.size());

        Example example = new Example(NoticeEntity.class);
        example.and()
                .andEqualTo(NoticeStatusEnum.NORMAL)
                .andEqualTo("type", CourseParams.TYPE);
        example.orderBy("id").asc();
        List<NoticeEntity> noticeEntities = noticeEntityMapper.selectByExample(example);
        log.error("obtain courseInfo list from database,size:{}", noticeEntities.size());
        for(NoticeEntity noticeEntity : noticeEntities){
            String custom = noticeEntity.getCustom();
            JSONObject jsonObject = JSONObject.parseObject(custom);
            if(jsonObject.containsKey(CourseParams.CLASS_TITLE)){
                continue;
            }
            if((!jsonObject.containsKey(Params.BIZ_ID)) || (!jsonObject.containsKey(CourseParams.TEACHER))){
                log.error("数据非法:{}", JSONObject.toJSONString(noticeEntity));
                continue;
            }
            String bizId = String.valueOf(jsonObject.get(Params.BIZ_ID));
            String teacher = String.valueOf(jsonObject.get(CourseParams.TEACHER));
            String key = bizId + teacher + simpleDate.format(noticeEntity.getCreateTime());
            CourseInfo courseInfo = courseInfoHashMap.getOrDefault(key, null);
            if(courseInfo == null){
                log.error("courseInfoHashMap does't contains key:{}", key);
                continue;
            }
            jsonObject.put(CourseParams.CLASS_TITLE, courseInfo.getClassTitle());
            NoticeEntity noticeEntity_ = new NoticeEntity();
            noticeEntity_.setId(noticeEntity.getId());
            noticeEntity_.setCustom(jsonObject.toJSONString());
            noticeEntity_.setCreateTime(noticeEntity.getCreateTime());
            noticeEntity_.setUpdateTime(new Timestamp(System.currentTimeMillis()));

            int execute = noticeEntityMapper.updateByPrimaryKeySelective(noticeEntity_);
            log.info("execute count:{}, id:{}",execute, noticeEntity_.getId());
        }
    }
}
