package com.sfwe496.gatewayserver.filter;

import com.sfwe496.gatewayserver.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ResponseFilter implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .doOnTerminate(() -> {
                    String correlationId = exchange.getRequest().getHeaders().getFirst(UserContext.CORRELATION_ID);

                    String status = exchange.getResponse().getStatusCode() != null
                            ? exchange.getResponse().getStatusCode().toString()
                            : "UNKNOWN";

                    logger.debug("ResponseFilter - Correlation ID: {}, Status: {}", correlationId, status);
                });
    }
}
