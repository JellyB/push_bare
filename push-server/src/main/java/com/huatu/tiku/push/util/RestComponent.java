package com.huatu.tiku.push.util;

import com.google.common.collect.Lists;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushErrors;
import com.huatu.tiku.push.constant.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * 描述：rest 接口常用方法
 *
 * @author biguodong
 * Create time 2018-11-11 下午4:42
 **/
@Component
public class RestComponent {

    private static final int SUCCESS_FLAG =  10000;
    @Value("${notice.push.order.buyUser}")
    private String orderBuyUserInfoUrl;

    @Autowired
    private RestTemplate restTemplate;


    public List<String> getOrderByUserName(String classId, int page){
        List<String> userInfoList = Lists.newArrayList();
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(orderBuyUserInfoUrl)
                    .queryParam("classId", classId)
                    .queryParam("page", page);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            HttpEntity<ResponseMsg> exchange =  restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, entity, ResponseMsg.class);
            ResponseMsg responseMsg = exchange.getBody();
            if(responseMsg.getCode() == SUCCESS_FLAG){
                if(null == responseMsg.getData()){
                    return userInfoList;
                }else{
                    userInfoList = (List<String>) responseMsg.getData();
                    return userInfoList;
                }
            }else{
                throw new BizException(NoticePushErrors.COURSE_ORDER_INFO_FETCH_ERROR);
            }
        }catch (Exception e){
            throw new BizException(NoticePushErrors.COURSE_ORDER_INFO_FETCH_ERROR);
        }
    }
}
