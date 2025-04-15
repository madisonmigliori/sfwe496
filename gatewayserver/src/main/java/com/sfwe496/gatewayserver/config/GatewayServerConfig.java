package com.sfwe496.gatewayserver.config;

import com.sfwe496.gatewayserver.filter.UserContextFilter;
import com.sfwe496.gatewayserver.filter.UserContextGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayServerConfig {

    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter();
    }

    @Bean
    public UserContextGlobalFilter userContextGlobalFilter() {
        return new UserContextGlobalFilter();
    }
}
