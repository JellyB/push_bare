package com.huatu.tiku.push.service.api.impl;

import com.huatu.tiku.push.constant.NoticePushRedisKey;
import com.huatu.tiku.push.dao.NoticeEntityMapper;
import com.huatu.tiku.push.entity.NoticeEntity;
import com.huatu.tiku.push.enums.NoticeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private NoticeEntityMapper noticeEntityMapper;

    /**
     * 预处理方法
     * 刷入消息数据 id && create_time to redis
     */
    //@PostConstruct
    public void preLoadingNoticeEntityData2Redis(){
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
