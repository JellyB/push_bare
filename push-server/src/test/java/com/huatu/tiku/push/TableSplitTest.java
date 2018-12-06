package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huatu.tiku.push.constant.CourseParams;
import com.huatu.tiku.push.manager.NoticeLandingManager;
import com.huatu.tiku.push.request.NoticeReq;
import com.huatu.tiku.push.service.api.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-04 下午3:12
 **/

@Slf4j
public class TableSplitTest extends PushBaseTest {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeLandingManager noticeLandingManager;


    @Test
    public void obtainNoticeListTest(){
        long userId = 192;
        PageInfo pageInfo =  noticeService.selectUserNotice(userId, 1, 20);
        log.info(JSONObject.toJSONString(pageInfo.getList()));
    }

    /**
     * 分表批量插入
     */
    @Test
    public void insertNoticeBatch(){
        final long noticeId = 100001L;
        Random random = new Random(100);
        List<NoticeReq.NoticeUserRelation> noticeUserRelationList = Lists.newArrayList();
        boolean isDone = true;
        while (isDone){
            if(noticeUserRelationList.size() == 100){
                isDone = false;
            }
            int userId = random.nextInt(200) + 1;
            NoticeReq.NoticeUserRelation noticeUserRelation = NoticeReq.NoticeUserRelation.builder()
                    .noticeId(noticeId)
                    .userId(Long.valueOf(userId))
                    .createTime(new Date())
                    .build();
            log.info("user:id:{}  >>>>>>>>> ", userId);
            noticeUserRelationList.add(noticeUserRelation);

        }
        CourseParams.Builder builder = CourseParams.Builder.builder().courseId("10000123").teacher("毕老师").build();
        NoticeReq noticeReq = NoticeReq.builder()
                .type(CourseParams.TYPE)
                .detailType(CourseParams.REMIND)
                .title("根据用户id数据库分表测试")
                .subTitle("spring boot mybatis interceptor数据库分表策略测试！")
                .text("I hope it work well!")
                .displayType(1)
                .custom(builder.getParams())
                .users(noticeUserRelationList)
                .build();


        noticeLandingManager.insert(noticeReq);
    }
}
