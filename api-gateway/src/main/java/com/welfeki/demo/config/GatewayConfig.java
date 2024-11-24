package com.welfeki.demo.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route for cours service
                .route("cours-service", r -> r
                        .path("/api/**")
                        .filters(f -> f
                                .removeRequestHeader("Origin")
                                .removeRequestHeader("Access-Control-Request-Method")
                                .removeRequestHeader("Access-Control-Request-Headers")
                        )
                        .uri("lb://COURS"))

                // Route for users service
                .route("users-service", r -> r
                        .path("/**")
                        .uri("lb://USERS-MICROSERVICE"))

                .build();
    }
}