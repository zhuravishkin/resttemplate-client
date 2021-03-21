package com.zhuravishkin.resttemplateclient.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class WebfluxWebClientTest {
    @Test
    void testHello() {
        RouterFunction function = RouterFunctions.route(
                RequestPredicates.GET("/webflux"),
                request -> ServerResponse
                        .ok()
                        .build()
        );
        WebTestClient
                .bindToRouterFunction(function)
                .build()
                .get()
                .uri("/webflux")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();
    }
}