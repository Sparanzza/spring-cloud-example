package com.springcloud.msvc.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {

    final String TOKEN = "token";

    private static final Logger log = LoggerFactory.getLogger(SampleGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    
        log.info("Global Pre Filter executed");
    
        var mutatedRequest = exchange.getRequest().mutate().headers(h -> {
            h.add(TOKEN, "myTokenValue");
        }).build();
    
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
    
        return chain.filter(mutatedExchange).then(Mono.fromRunnable(() -> {
            // El header "token" estará presente en la solicitud mutada
            Optional.ofNullable(mutatedExchange.getRequest().getHeaders().getFirst(TOKEN))
                    .ifPresent(token -> {
                        log.info("Token: {}", token);
                        mutatedExchange.getResponse().getHeaders().add(TOKEN, token);
                    });
            mutatedExchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
            // mutatedExchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }

}
