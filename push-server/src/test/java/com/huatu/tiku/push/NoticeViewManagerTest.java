package com.huatu.tiku.push;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.entity.NoticeView;
import com.huatu.tiku.push.manager.NoticeViewManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-05-09 1:26 PM
 **/
@Slf4j
public class NoticeViewManagerTest extends PushBaseTest{

    @Autowired
    private NoticeViewManager noticeViewManager;

    /**
     * 数据排查
     * SELECT t.user_id,t.`view`,count(t.user_id) AS cut FROM t_notice_view t WHERE t.`status`=1 GROUP BY t.user_id,t.`view` HAVING cut> 1
     */
    @Test
    public void obtainNoticeViewTest(){
        long userId = 236179489;
        String view = "feedBack";
        Optional<NoticeView> optionalNoticeView =  noticeViewManager.obtainNoticeView(userId, view);
        log.info("view info:{}", JSONObject.toJSONString(optionalNoticeView));
    }
}
