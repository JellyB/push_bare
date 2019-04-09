package com.huatu.tiku.push.cast.strategy;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.huatu.tiku.push.cast.HttpClientStrategy;
import com.huatu.tiku.push.cast.PushResult;
import com.huatu.tiku.push.cast.RestTemplateStrategy;
import com.huatu.tiku.push.cast.UmengNotification;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import com.huatu.tiku.push.enums.NoticeParentTypeEnum;
import com.huatu.tiku.push.enums.NoticeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-20 下午8:25
 **/
@Slf4j
public abstract class AbstractPushTemplate implements PushStrategy{


    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    private HttpClientStrategy httpClientStrategy;

    private List<UmengNotification> notificationList;

    public List<UmengNotification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<UmengNotification> notificationList) {
        this.notificationList = notificationList;
    }

    public String getPushResultKey(NoticeTypeEnum noticeTypeEnum, long bizId){
        return NoticePushRedisKey.getPushResultKey(noticeTypeEnum.getType().getType(), noticeTypeEnum.getDetailType(), String.valueOf(bizId));
    }


    /**
     * 处理push 结果
     * @param pushResult
     * @param bizId
     * @param noticeTypeEnum
     * @param simpleName
     */
    private final void dealPushResult(String simpleName, NoticeTypeEnum noticeTypeEnum, long bizId, PushResult pushResult){
        if(noticeTypeEnum.getType().equals(NoticeParentTypeEnum.FEEDBACK)){
            return;
        }
        if(noticeTypeEnum.getType().equals(NoticeParentTypeEnum.ORDER)){
            return;
        }
        try{
            String result_id = "";
            if(pushResult.getRet().equals(SUCCESS)){
                if(pushResult.getData().containsKey(TASK_ID)){
                    result_id = String.valueOf(pushResult.getData().get(TASK_ID));
                    result_id = Joiner.on(NoticePushRedisKey.SELF_SEPARATOR).join(TASK_ID, simpleName, result_id);
                }
                if(pushResult.getData().containsKey(MSG_ID)){
                    result_id = String.valueOf(pushResult.getData().get(MSG_ID));
                    result_id = Joiner.on(NoticePushRedisKey.SELF_SEPARATOR).join(MSG_ID, simpleName, result_id);
                }
            }
            if(pushResult.getRet().equals(FAIL)){
                result_id = pushResult.getData().toJSONString();
                log.error("error_code:{},error_msg:{}",
                        pushResult.getData().getString("error_code"),
                        pushResult.getData().getString("error_msg"));
            }
            log.info("push result_id:{}", result_id);
            SetOperations setOperations = redisTemplate.opsForSet();
            String key = getPushResultKey(noticeTypeEnum, bizId);
            long members = setOperations.add(key, result_id);
            boolean result = redisTemplate.expire(key, NoticePushRedisKey.PUSH_RESULT_EXPIRE_TIME, TimeUnit.DAYS);
            log.info("push result, key:{}, members:{}, result:{}", key, members,result);
        }catch (Exception e){
            log.error("dealPushResult error!", e);
        }

    }
    /**
     * 推送接口
     * @param noticeTypeEnum
     * @param bizId 业务id:
     *              course:liveId
     *
     *
     */
    @Override
    public final void push(NoticeTypeEnum noticeTypeEnum, long bizId) {
        try{
            log.info("推送任务当前线程名:{}", Thread.currentThread().getName());
            if(CollectionUtils.isEmpty(getNotificationList())){
                log.error("notifications can not be empty!!");
            }
            SetOperations<String, UmengNotification> setOperations = redisTemplate.opsForSet();
            String key = NoticePushRedisKey.getCourseLiveId(bizId);
            redisTemplate.expire(key, 1, TimeUnit.HOURS);
            getNotificationList().forEach(item->{
                if(setOperations.isMember(key, item)){
                    log.error("推送重复数据:{}", JSONObject.toJSONString(item));
                    return;
                }else{
                    String simpleName = item.getClass().getSimpleName();
                    PushResult pushResult = httpClientStrategy.send(item);
                    dealPushResult(simpleName, noticeTypeEnum, bizId, pushResult);
                    setOperations.add(key, item);
                }

            });
        }catch (Exception e){
            log.error("push main function error!", e);
        }

    }
}
