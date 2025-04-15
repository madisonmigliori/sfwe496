package com.sfwe496.gatewayserver.filter;

import com.sfwe496.gatewayserver.utils.UserContext;
import com.sfwe496.gatewayserver.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class TrackingFilter implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String correlationId = exchange.getRequest().getHeaders().getFirst(UserContext.CORRELATION_ID);
        String authToken = exchange.getRequest().getHeaders().getFirst(UserContext.AUTH_TOKEN);


        UserContext context = UserContextHolder.getContext();
        context.setCorrelationId(correlationId != null ? correlationId : java.util.UUID.randomUUID().toString());
        context.setAuthToken(authToken);

        logger.debug("TrackingFilter - Correlation ID: {}", context.getCorrelationId());

        exchange.getResponse().getHeaders().add(UserContext.CORRELATION_ID, context.getCorrelationId());

        return chain.filter(exchange);
    }
}
