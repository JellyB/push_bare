package com.huatu.tiku.push;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * 描述：
 *
 * @author biguodong
 * Create time 2019-06-07 4:39 PM
 **/

@Slf4j
public class SpilderTest {

    public static void main(String[] args) {
        String url = "https://www.icloud.com/sharedalbum/zh-cn/?from=groupmessage&isappinstalled=0#B0OG6XBubZrOLh";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
        //uriComponentsBuilder.queryParam("from", "groupmessage");
        //uriComponentsBuilder.queryParam("isappinstalled", "0#B0OG6XBubZrOLh");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<String> exchange = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, entity, String.class);
        System.err.println(exchange.getBody());
    }
}
