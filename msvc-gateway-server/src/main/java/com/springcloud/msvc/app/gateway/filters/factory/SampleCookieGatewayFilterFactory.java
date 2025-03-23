package com.springcloud.msvc.app.gateway.filters.factory;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class SampleCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigurationCookie> {

    public static class ConfigurationCookie {
        private String name;
        private String value;
        private String message;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }


    private final Logger logger = Logger.getLogger(SampleCookieGatewayFilterFactory.class.getName());

    public SampleCookieGatewayFilterFactory() {
        super(ConfigurationCookie.class);
    }

    @Override
    public GatewayFilter apply(ConfigurationCookie config) {

        return new OrderedGatewayFilter((exchange, chain) -> {
            logger.info(null != config.getMessage() ? config.getMessage() : "Cookie Gateway Filter executed");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> 
                Optional.ofNullable(config.getName()).ifPresent(name -> {
                    exchange.getResponse().getCookies().add(name, ResponseCookie.from(name, config.getValue()).build());
                    logger.info("Cookie added " + name + " : " + config.getMessage());
                })
            ));
        }, 100);
    }
}
