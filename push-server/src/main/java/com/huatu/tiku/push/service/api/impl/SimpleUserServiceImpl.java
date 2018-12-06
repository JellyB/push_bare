package com.huatu.tiku.push.service.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.cast.FileUploadTerminal;
import com.huatu.tiku.push.cast.PushService;
import com.huatu.tiku.push.constant.CourseParams;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.entity.SimpleUser;
import com.huatu.tiku.push.manager.SimpleUserManager;
import com.huatu.tiku.push.service.api.SimpleUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 描述：用户信息存储服务
 *
 * @author biguodong
 * Create time 2018-11-13 下午9:17
 **/
@Slf4j
@Service
public class SimpleUserServiceImpl implements SimpleUserService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SimpleUserManager simpleUserManager;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PushService pushService;

    private static final Long DEFAULT_EXPIRE_TIME = 5 * 60 * 1000L;


    /**
     * 入库 & redis
     * @param classId
     * @param liveId
     * @param simpleUserList
     * @param endTime
     */
    @Override
    public void storeSimpleUser(long classId, long liveId, List<SimpleUser> simpleUserList, long endTime) {
        /**
         * redis set 存储一份
         */
        long expireTime = endTime - System.currentTimeMillis();
        if(expireTime < 0){
            expireTime = DEFAULT_EXPIRE_TIME;
        }
        List<Long> userIds = simpleUserList.stream()
                .map(SimpleUser::getUserId)
                .collect(Collectors.toList());
        String redis = NoticePushRedisKey.getCourseClassId(classId);
        SetOperations setOperations = redisTemplate.opsForSet();
        long members = setOperations.add(redis, userIds.toArray());
        /**
         * 判断是否需要上传文件；
         */
        if(members == 0){
            judgeFileUpload(classId, liveId);
        }
        redisTemplate.expire(redis, expireTime, TimeUnit.MILLISECONDS);
        log.info("simple user into redis: count:{}", members);

        List<SimpleUser> dataBaseList = simpleUserManager.selectByUserIds(userIds, CourseParams.TYPE, classId);
        if(CollectionUtils.isNotEmpty(dataBaseList)){
            Set<Long> existIds = dataBaseList.stream().map(SimpleUser::getUserId).collect(Collectors.toSet());
            List<SimpleUser> filerList = simpleUserList.stream().filter(simpleUser -> !existIds.contains(simpleUser.getUserId())).collect(Collectors.toList());
            simpleUserList.clear();
            simpleUserList.addAll(filerList);
        }
        if(CollectionUtils.isEmpty(simpleUserList)){
            return;
        }
        if(simpleUserList.size() < RabbitMqKey.USER_STORE_THRESHOLD){
            simpleUserManager.saveSimpleUsers(simpleUserList);
        }else{
            simpleUserList.forEach(item->{
                String message = JSONObject.toJSONString(item);
                rabbitTemplate.convertAndSend(RabbitMqKey.NOTICE_USER_STORING, message);
            });
        }
    }


    /**
     * 如果redis members size > 100
     * @param classId
     * @param liveId
     */
    @Async
    @Override
    public void judgeFileUpload(long classId, long liveId)throws BizException{
        try{
            String redis = NoticePushRedisKey.getCourseClassId(classId);
            SetOperations setOperations = redisTemplate.opsForSet();
            String hasKey = String.valueOf(liveId);
            long size = setOperations.size(redis);
            if(size > RabbitMqKey.PUSH_STRATEGY_THRESHOLD){
                String key = NoticePushRedisKey.getCourseFileId(String.valueOf(classId));
                HashOperations hashOperations = redisTemplate.opsForHash();
                FileUploadTerminal fileUploadTerminal = pushService.uploadTerminal(classId);
                String value = JSONObject.toJSONString(fileUploadTerminal);
                hashOperations.put(key, hasKey, value);
            }
        }catch (Exception e){
            log.error("store upload file id into redis error!", e);
        }

    }

    /**
     * 获取文件上传id(redis)
     *
     * @param classId
     * @return
     * @throws BizException
     */
    @Override
    public FileUploadTerminal obtainFileUpload(long classId, long liveId) throws BizException {
        try{
            HashOperations hashOperations = redisTemplate.opsForHash();
            String key = NoticePushRedisKey.getCourseFileId(String.valueOf(classId));
            String hasKey = String.valueOf(liveId);
            String hashValue = String.valueOf(hashOperations.get(key, hasKey));
            FileUploadTerminal fileUploadTerminal = JSONObject.parseObject(hashValue, FileUploadTerminal.class);
            return fileUploadTerminal;
        }catch (Exception e){
            log.error("obtain notice.push.file id error!", e);
        }
        return null;
    }
}
