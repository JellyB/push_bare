package com.huatu.tiku.push.service.api.impl;

import com.google.common.collect.Lists;
import com.huatu.tiku.push.constant.CourseParams;
import com.huatu.tiku.push.constant.CourseQueueEntity;
import com.huatu.tiku.push.constant.SimpleUserInfo;
import com.huatu.tiku.push.constant.SimpleUserWithBizId;
import com.huatu.tiku.push.entity.SimpleUser;
import com.huatu.tiku.push.service.api.CourseUserInfoComponent;
import com.huatu.tiku.push.service.api.SimpleUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：调用PHP服务获取购买class 的userNames
 *
 * @author biguodong
 * Create time 2018-11-08 上午10:29
 **/
@Slf4j
@Service
public class CourseUserInfoComponentImpl implements CourseUserInfoComponent {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimpleUserService simpleUserService;

    @Value("${notice.push.order.buyUser}")
    private String orderBuyUser;

    @Value("${notice.push.order.user.ids}")
    private String orderBuyUserIdsUrl;

    private static final String CLASS_ID = "classId";

    private static final String PAGE = "page";


    private static final long SUCCESS_FLAG_USER = 1000000;

    private static final long SUCCESS_FLAG_PHP = 10000;

    private static final int HasNext = 1;



    @Override
    public void fetchUserName(CourseQueueEntity courseQueueEntity) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(orderBuyUser);
            uriComponentsBuilder.queryParam(CLASS_ID, courseQueueEntity.getClassId());
            uriComponentsBuilder.queryParam(PAGE, courseQueueEntity.getDealPage().get());
            HttpEntity<?> entity = new HttpEntity<>(headers);
            HttpEntity<PhpResponse> exchange = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, entity, PhpResponse.class);
            PhpResponse response = exchange.getBody();
            if(null != response && null != response.getData() && response.getCode() == SUCCESS_FLAG_PHP){
                List<String> userNames = response.getData().getUserName();
                if(CollectionUtils.isNotEmpty(userNames)){
                    List<SimpleUserInfo> list = Lists.newArrayList();
                    userNames.forEach(item -> list.add(SimpleUserInfo.builder().userName(item).build()));
                    SimpleUserWithBizId simpleUserWithBizId = SimpleUserWithBizId
                            .builder()
                            .classId(courseQueueEntity.getClassId())
                            .liveId(courseQueueEntity.getLiveId())
                            .endTime(courseQueueEntity.getEndTime().getTime())
                            .data(list)
                            .build();

                    fetchUserId(simpleUserWithBizId);
                }
                if(response.getData().getNext() == HasNext){
                    final CourseQueueEntity newQueueEntity = new CourseQueueEntity();
                    BeanUtils.copyProperties(courseQueueEntity, newQueueEntity);
                    AtomicInteger dealPage = new AtomicInteger(courseQueueEntity.getDealPage().incrementAndGet());
                    newQueueEntity.setDealPage(dealPage);
                    /**
                     * 递归，如果页数较多，可能会对php接口压力比较大
                     */
                    fetchUserName(newQueueEntity);
                }
            }
        }catch (Exception e){
            log.error("get user name from php error", e);
        }
    }



    @Override
    public void fetchUserId(SimpleUserWithBizId simpleUserWithBizId) {
        try {
            List<String> userNames = Lists.newArrayList();
            simpleUserWithBizId.getData().forEach(item -> userNames.add(item.getUserName()));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            MultiValueMap<String, List> map= new LinkedMultiValueMap<>();
            map.add("userNames", userNames);
            HttpEntity<List<String>> request = new HttpEntity<>(userNames, headers);

            ResponseEntity<UserResponse> responseEntity = restTemplate.postForEntity(orderBuyUserIdsUrl, request, UserResponse.class);
            UserResponse userResponse = responseEntity.getBody();

            if(Long.valueOf(userResponse.getCode()) == SUCCESS_FLAG_USER && CollectionUtils.isNotEmpty(userResponse.getData())){
                storeUserInfo(simpleUserWithBizId.getClassId(), simpleUserWithBizId.getLiveId(), userResponse, simpleUserWithBizId.getEndTime());
            }

        }catch (Exception e){
            log.error("get user id from user service error", e);
        }
    }

    /**
     * 入库
     * @param response
     * @param classId
     * @param liveId
     */
    private void storeUserInfo(long classId, long liveId, UserResponse response, long endTime){
        List<SimpleUser> list = Lists.newArrayList();
        response.getData().forEach(item -> {
            SimpleUser simpleUser = SimpleUser
                    .builder()
                    .userId(item.getUserId())
                    .userName(item.getUserName())
                    .userType(CourseParams.TYPE)
                    .bizId(classId)
                    .build();
            list.add(simpleUser);
        });
        simpleUserService.storeSimpleUser(classId, liveId, list, endTime);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class User{
        private String userName;
        private Long userId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class UserResponse{
        private List<User> data;
        private String code;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhpResponse implements Serializable{
        private String msg;

        private long code;

        private PhpResponseData data;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhpResponseData implements Serializable{

        private int next;

        private List<String> userName;
    }
}
