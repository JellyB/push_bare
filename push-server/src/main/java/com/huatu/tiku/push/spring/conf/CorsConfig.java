package com.huatu.tiku.push.spring.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-12-26 下午4:32
 **/

@Component
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        /**
         * 允许任何域名使用
         */
        corsConfiguration.addAllowedOrigin("*");
        /**
         * 允许任何头
         */
        corsConfiguration.addAllowedHeader("*");
        /**
         * 允许任何方法（post、get等）
         */
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
