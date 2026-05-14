package com.ecommerce.apigateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.rmi.server.UID;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements GlobalFilter {
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    @Override
    public Mono<Void> filter(ServerWebExchange http, GatewayFilterChain chain) {
        String correlationId = UUID.randomUUID().toString();
        http.getRequest().mutate()
                .header(CORRELATION_ID_HEADER, correlationId)
                .build();
        http.getResponse().getHeaders().add(CORRELATION_ID_HEADER, correlationId);
        return chain.filter(http);
    }
}
