//package com.example.SocialMediaApiGateway.config;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Configuration
//public class ApiGatewayConfig {
//
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//
//                .route("user-service", r -> r.path("/users/**")
//                        .filters(f -> f
//                                .addRequestHeader("X-Gateway-Request", "true")
//                                .filter(this::modifyRequestHeaders)
//                        )
//                        .uri("lb://SOCIALMEDIAUSER")
//                )
//
//                .route("likes-service", r -> r.path("/likes/**")
//                        .filters(f -> f
//                                .addRequestHeader("X-Gateway-Request", "true")
//                                .filter(this::modifyRequestHeaders)
//                        )
//                        .uri("lb://SOCIALMEDIALIKE")
//                )
//
//                .route("notifications-service", r -> r.path("/notifications/**")
//                        .filters(f -> f
//                                .addRequestHeader("X-Gateway-Request", "true")
//                                .filter(this::modifyRequestHeaders)
//                        )
//                        .uri("lb://SOCIALMEDIANOTIFICATION")
//                )
//
//                .route("post-service", r -> r.path("/posts/**")
//                        .filters(f -> f
//                                .addRequestHeader("X-Gateway-Request", "true")
//                                .filter(this::modifyRequestHeaders)
//                        )
//                        .uri("lb://SOCIALMEDIAPOST")
//                )
//
//                .route("comment-service", r -> r.path("/comment/**")
//                        .filters(f -> f
//                                .addRequestHeader("X-Gateway-Request", "true")
//                                .filter(this::modifyRequestHeaders)
//                        )
//                        .uri("lb://SOCIALMEDIACOMMENT")
//                )
//                .build();
//    }
//
//    private Mono<Void> modifyRequestHeaders(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//        String gatewayHeader = exchange.getRequest().getHeaders().getFirst("X-Gateway-Request");
//        System.out.println("Authorization header: " + authHeader);
//        System.out.println("X-Gateway-Request header: " + gatewayHeader);
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            exchange = exchange.mutate()
//                    .request(r -> r.header("Authorization", authHeader))
//                    .build();
//        } else {
//            return Mono.error(new IllegalArgumentException("Authorization header missing or invalid"));
//        }
//        return chain.filter(exchange);
//    }
//
//}
