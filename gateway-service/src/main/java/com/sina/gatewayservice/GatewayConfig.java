package com.sina.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("voice_chat_route", r -> r
                        .path("/ws/voice-chat-signaling/**")
                        .filters(f -> f.rewritePath("/ws/voice-chat-signaling/(?<remaining>.*)", "/${remaining}"))
                        .uri("ws://localhost:8082")
                )
                .build();
    }
}