package com.zhuravishkin.resttemplateclient.config;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebfluxWebClient {
    private final WebClient CLIENT = WebClient.create("http://localhost:8080");

    private Mono<ClientResponse> result = CLIENT.get()
            .uri("/webflux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    public String getResult() {
        return ">> result = " + result
                .flatMap(res -> res.bodyToMono(String.class))
                .block();
    }
}
