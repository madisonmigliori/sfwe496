package com.sfwe496.gatewayserver.filter;

import com.sfwe496.gatewayserver.utils.UserContext;
import com.sfwe496.gatewayserver.utils.UserContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserContextFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String correlationId = httpServletRequest.getHeader(UserContext.CORRELATION_ID);
        String authToken = httpServletRequest.getHeader(UserContext.AUTH_TOKEN);

        UserContext context = UserContextHolder.getContext();
        context.setCorrelationId(correlationId != null ? correlationId : java.util.UUID.randomUUID().toString());
        context.setAuthToken(authToken);

        logger.debug("UserContextFilter - Correlation ID: {}", context.getCorrelationId());

        httpServletResponse.setHeader(UserContext.CORRELATION_ID, context.getCorrelationId());

        chain.doFilter(request, response);
    }
}
