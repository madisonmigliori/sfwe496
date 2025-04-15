package com.sfwe496.gatewayserver.config;

import com.sfwe496.gatewayserver.filter.TrackingFilter;
import com.sfwe496.gatewayserver.filter.ResponseFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterConfig {

    @Bean
    public TrackingFilter trackingFilter() {
        return new TrackingFilter();
    }

    @Bean
    public ResponseFilter responseFilter() {
        return new ResponseFilter();
    }
}
