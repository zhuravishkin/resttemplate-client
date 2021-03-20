package com.zhuravishkin.resttemplateclient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuravishkin.resttemplateclient.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping(value = "client")
public class Controller {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Controller(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<User> get() throws JsonProcessingException, URISyntaxException {
        try {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "no body");
        } catch (HttpClientErrorException e) {
            log.warn(String.valueOf(e.getRawStatusCode()));
            log.warn(e.getLocalizedMessage());
            log.warn(e.getStatusCode().toString());
            log.warn(e.getStatusText());
            log.warn(e.getStatusCode().getReasonPhrase());
            log.warn(e.getMessage());
        }

        String url = "http://localhost:8080/server";

        User getUser = restTemplate.getForEntity(url + "/get", User.class).getBody();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(getUser), httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .uri(new URI(url))
                .pathSegment("post")
                .queryParam("surname", "Winchester")
                .build()
                .encode();

        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(
                uriComponents.toString(),
                httpEntity,
                User.class);

        log.warn(userResponseEntity.getBody().toString());

        return userResponseEntity;
    }
}
