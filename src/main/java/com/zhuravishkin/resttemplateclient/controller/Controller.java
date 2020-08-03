package com.zhuravishkin.resttemplateclient.controller;

import com.zhuravishkin.resttemplateclient.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping(value = "client")
public class Controller {
    @GetMapping(value = "/get")
    public ResponseEntity<User> get() {
        try {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "no body");
        } catch (HttpClientErrorException e) {
            log.error(String.valueOf(e.getRawStatusCode()));
            log.error(e.getLocalizedMessage());
            log.error(e.getStatusCode().toString());
            log.error(e.getStatusText());
            log.error(e.getStatusCode().getReasonPhrase());
            log.error(e.getMessage());
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/template";
        User getUser = restTemplate.getForEntity(url + "/get", User.class).getBody();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, httpHeaders);
        map.add("name", getUser.getName() + " Winchester");
        int age = getUser.getAge();
        map.add("age", String.valueOf(++age));
        return restTemplate.postForEntity(url + "/post", httpEntity, User.class);
    }
}
