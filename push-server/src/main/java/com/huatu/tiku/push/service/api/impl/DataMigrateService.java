package com.huatu.tiku.push.service.api.impl;

import com.google.common.collect.Maps;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import com.huatu.tiku.push.dao.NoticeEntityMapper;
import com.huatu.tiku.push.entity.NoticeEntity;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import com.huatu.tiku.push.response.DataMigrateResp;
import com.huatu.tiku.push.service.feign.DataMigrateFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-07 下午5:23
 **/
@Service
@Slf4j
public class DataMigrateService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DataMigrateFeign dataMigrateFeign;

    @Autowired
    private NoticeEntityMapper noticeEntityMapper;




    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;


    public void execute(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String pageKey = NoticePushRedisKey.getDataMigratePageNum();
        String pageSizeKey = NoticePushRedisKey.getDataMigrateSizeCount();
        if((!redisTemplate.hasKey(pageKey)) || (!redisTemplate.hasKey(pageSizeKey))){
            log.error("page info:page && size is empty!");
            throw new BizException(NoticePushErrors.MIGRATE_PAGE_INFO_EMPTY);
        }
        int page = Integer.valueOf(String.valueOf(valueOperations.get(pageKey)));
        int size = Integer.valueOf(String.valueOf(valueOperations.get(pageSizeKey)));
        try{

            Map<String, Object> params = Maps.newHashMap();
            params.put("page", page);
            params.put("size", size);

            DataMigrateResp dataMigrateResp = dataMigrateFeign.migrate(params);
            DataMigrateResp.InnerData innerData = dataMigrateResp.getData();
            if(innerData.getNext() > 0 && innerData.getList().size() > 0){
                page ++;
                valueOperations.set(pageKey, String.valueOf(page));
            }else{
                redisTemplate.expire(pageKey, 100, TimeUnit.SECONDS);
                redisTemplate.expire(pageSizeKey, 100, TimeUnit.SECONDS);
                TriggerKey triggerKey = new TriggerKey("pandoraMigrate2Push", "Migrate");
                JobKey jobKey = new JobKey("pandoraMigrate2Push", "Migrate");
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                scheduler.deleteJob(jobKey);
            }
        }catch (Exception e){
            log.error("******************************");
            log.error("> 远程调用pandora 异常，没有可用实例 <");
            log.error("******************************");
        }
    }

    /**
     * 刷入消息数据 id && create_time to redis
     */


    public void initNoticeEntityData2Redis(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = NoticePushRedisKey.getDataMigrateNoticeCreateTime();
        Example example = new Example(NoticeEntity.class);
        example.and().andEqualTo("status", NoticeStatusEnum.NORMAL.getValue());
        example.orderBy("id").asc();
        List<NoticeEntity> list = noticeEntityMapper.selectByExample(example);
        list.forEach(noticeEntity -> {
            String hashKey = String.valueOf(noticeEntity.getId());
            String value = String.valueOf(noticeEntity.getCreateTime().getTime());
            hashOperations.put(key, hashKey, value);
        });
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }
}
