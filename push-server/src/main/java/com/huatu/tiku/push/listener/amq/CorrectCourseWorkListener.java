package com.huatu.tiku.push.listener.amq;

import com.alibaba.fastjson.JSONObject;
import com.huatu.tiku.push.constant.CorrectCourseWorkPushInfo;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import com.huatu.tiku.push.constant.RabbitMqKey;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReportService;
import com.huatu.tiku.push.service.api.CorrectCourseWorkReturnService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述：申论课后作业消息通知
 *
 * @author biguodong
 * Create time 2018-12-11 下午1:51
 **/
@Component
@RabbitListener(queues = RabbitMqKey.NOTICE_CORRECT_COURSE_WORK)
@Slf4j
public class CorrectCourseWorkListener {

    @Autowired
    private CorrectCourseWorkReturnService correctCourseWorkReturnService;

    @Autowired
    private CorrectCourseWorkReportService correctCourseWorkReportService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notice.push.course.cover}")
    private String courseCoverUrl;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String DEFAULT_IMG = "http://p.htwx.net/images/course_default_500x360.jpg";

    @RabbitHandler
    public void onMessage(String message){
        CorrectCourseWorkPushInfo pushInfo = JSONObject.parseObject(message, CorrectCourseWorkPushInfo.class);
        log.info("申论课后作业批改推送内容:{}", JSONObject.toJSONString(pushInfo));
        pushInfo.setImg(dealNetClassCover(pushInfo.getNetClassId()));
        if(pushInfo.getType().equals(CorrectCourseWorkPushInfo.RETURN)){
            correctCourseWorkReturnService.send(pushInfo);
        }
        if(pushInfo.getType().equals(CorrectCourseWorkPushInfo.REPORT)){
            correctCourseWorkReportService.send(pushInfo);
        }
    }

    /**
     * 获取课程封面 url
     * @param netClassId
     * @return
     */
    private String dealNetClassCover(long netClassId){
        String key = NoticePushRedisKey.getNoticeCourseCoverKey(netClassId);
        if(redisTemplate.hasKey(key)){
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            return valueOperations.get(key);
        }else{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(courseCoverUrl);
            uriComponentsBuilder.queryParam("classId", netClassId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            HttpEntity<PhpResponse> exchange = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, entity, PhpResponse.class);
            PhpResponse phpResponse = exchange.getBody();
            String value = MapUtils.getString(phpResponse.getData(), "scaleImg", DEFAULT_IMG);
            return value;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhpResponse implements Serializable {
        private String msg;

        private long code;

        private Map<String, Object> data;
    }
}
