package com.huatu.tiku.push.service.api.impl;

import com.huatu.tiku.push.constant.LiveCourseInfo;
import com.huatu.tiku.push.service.api.CourseInfoComponent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-11-11 3:05 PM
 **/

@Component
@Slf4j
public class CourseInfoComponentImpl implements CourseInfoComponent{


    @Autowired
    private RestTemplate restTemplate;

    @Value("${notice.push.course.live.info}")
    private String infoByLiveId;


    @Override
    public LiveCourseInfo fetchCourseInfo(Long netClassId, Long liveCourseWareId) {
        return getPhpResponse(netClassId, liveCourseWareId).getData();
    }

    /**
     * 获取 php 结果
     * @param netClassId
     * @param liveCourseWareId
     * @return
     */
    private PhpResponse getPhpResponse(Long netClassId, Long liveCourseWareId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(infoByLiveId);
        uriComponentsBuilder.queryParam("netClassId", netClassId);
        uriComponentsBuilder.queryParam("liveCoursewareId", liveCourseWareId);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<PhpResponse> exchange = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, entity, PhpResponse.class);
        return exchange.getBody();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhpResponse implements Serializable {
        private String msg;

        private long code;

        private LiveCourseInfo data;
    }
}
