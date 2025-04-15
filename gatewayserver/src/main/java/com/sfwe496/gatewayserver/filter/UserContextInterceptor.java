package com.sfwe496.gatewayserver.filter;

import com.sfwe496.gatewayserver.utils.UserContext;
import com.sfwe496.gatewayserver.utils.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserContextInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        UserContext context = UserContextHolder.getContext();
        template.header(UserContext.CORRELATION_ID, context.getCorrelationId());
        template.header(UserContext.AUTH_TOKEN, context.getAuthToken());
    }
}
