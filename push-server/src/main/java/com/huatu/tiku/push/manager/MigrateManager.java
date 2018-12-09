package com.huatu.tiku.push.manager;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.annotation.SplitParam;
import com.huatu.tiku.push.dao.NoticeUserMapper;
import com.huatu.tiku.push.entity.NoticeUserRelation;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 下午4:57
 **/
@Component
@Slf4j
public class MigrateManager {

    @Autowired
    private NoticeUserMapper noticeUserMapper;


    /**
     * 关系表插入逻辑
     * @param message
     */
    public void insertRelation(String message){
        try{
            NoticeUserRelation noticeUserRelation = JSONObject.parseObject(message, NoticeUserRelation.class);
            noticeUserRelation.setId(null);
            boolean checkExist = ((MigrateManager)AopContext.currentProxy()).checkDataExist(noticeUserRelation.getUserId(), noticeUserRelation.getNoticeId());
            if(checkExist){
                return;
            }else{
                ((MigrateManager)AopContext.currentProxy()).insert(noticeUserRelation.getUserId(), noticeUserRelation);
            }
        }catch (Exception e){

        }
    }

    @SplitParam
    public int insert(long userId, NoticeUserRelation noticeUserRelation){
        try{
            noticeUserRelation.setId(null);
            return noticeUserMapper.insert(noticeUserRelation);
        }catch (Exception e){
            log.error("*******************************");
            log.error(">>>>> 消费队列插入mysql 异常！<<<<");
            log.error("*******************************");
            log.error("{}", e);
        }
        return 0;
    }


    /**
     * 检查数据是否存在
     * @param userId
     * @param noticeId
     * @return
     */
    @SplitParam
    public boolean checkDataExist(long userId, long noticeId){
        try{
            Example example = new Example(NoticeUserRelation.class);
            example.and()
                    .andEqualTo("userId", userId)
                    .andEqualTo("noticeId", noticeId)
                    .andEqualTo("status", NoticeStatusEnum.NORMAL.getValue());

            Object object =  noticeUserMapper.selectOneByExample(example);
            return null != object;
        }catch (Exception e){
            log.error("*******************************");
            log.error(">>>>>> 检查数据是否存在异常 <<<<<<");
            log.error("*******************************");
            log.error("{}", e);
        }
        return false;
    }
}
