package com.springcloud.msvc.app.gateway.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .authorizeExchange(authz -> authz.pathMatchers("/authorized", "logout").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/items", "/api/users", "/api/products").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/items/{id}", "/api/users/{id}", "/api/products/{id}")
                        .hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/items/**", "/api/users/**", "/api/products/**")
                        .hasAnyRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/items/**", "/api/users/**", "/api/products/**")
                        .hasAnyRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/items/{id}", "/api/users/{id}", "/api/products/{id}")
                        .hasAnyRole("ADMIN").anyExchange().authenticated())
                .csrf(CsrfSpec::disable)
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
