package com.example.gateway.demogateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Spring Cloud Gateway  forward-routing-filterテスト用
 * https://spring.pleiades.io/spring-cloud-gateway/docs/current/reference/html/#forward-routing-filter
 */
@Configuration
public class DemoWebFluxRouter {

    @Bean
    RouterFunction router() {
        return RouterFunctions.route().GET("/forward",
                request -> ServerResponse
                        .ok()
                        .body(Mono.just("forward handler"), String.class))
                .build();
    }
}
