package com.huatu.tiku.push.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.huatu.common.SuccessMessage;
import com.huatu.tiku.push.cast.AndroidCustomCast;
import com.huatu.tiku.push.cast.IosCustomCast;
import com.huatu.tiku.push.cast.strategy.PushStrategy;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-25 下午10:43
 **/

@Slf4j
@RequestMapping(value = "push")
@RestController
public class PushController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IosCustomCast iosCustomCast;

    @Autowired
    private AndroidCustomCast androidCustomCast;


    @GetMapping("result")
    public Object getResult(@RequestParam(value = "type") String type,
                            @RequestParam(value = "detailType")String detailType,
                            @RequestParam(value = "bizId") long bizId){
        Map<String, JSONObject> result = Maps.newHashMap();
        String redisKey =  NoticePushRedisKey.getPushResultKey(type, detailType, String.valueOf(bizId));
        if(!redisTemplate.hasKey(redisKey)){
           return SuccessMessage.create("数据查询不到！");
        }
        SetOperations setOperations = redisTemplate.opsForSet();
        Set<String> members = setOperations.members(redisKey);
        if(CollectionUtils.isEmpty(members)){
            return SuccessMessage.create("推送结果数据为空！");
        }
        Iterator<String> iterator = members.iterator();
        while(iterator.hasNext()){
            String item = iterator.next();
            if(item.startsWith(PushStrategy.MSG_ID)){
                return result;
            }
            String[] data = item.split(NoticePushRedisKey.SELF_SEPARATOR);
            if(data[1].equals(PushStrategy.IOS)){
                result.put(PushStrategy.IOS, JSONObject.parseObject(iosCustomCast.getTaskStatus(data[2])));
            }
            if(data[1].equals(PushStrategy.ANDROID)){
                result.put(PushStrategy.ANDROID, JSONObject.parseObject(androidCustomCast.getTaskStatus(data[2])));
            }

        }
        return result;
    }

}
