package com.zhuravishkin.resttemplateclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuravishkin.resttemplateclient.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Controller.class)
class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private Controller controller;

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void get_ok() {
        User user = new User("Sam", 30);
        ResponseEntity<User> responseEntity = new ResponseEntity<>(user, HttpStatus.OK);

        when(restTemplate.getForEntity("http://localhost:8080/server/get", User.class))
                .thenReturn(responseEntity);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(responseEntity.getBody()), httpHeaders);

        when(restTemplate.postForEntity("http://localhost:8080/server/post?surname=Winchester", httpEntity, User.class))
                .thenReturn(responseEntity);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .pathSegment("client", "get")
                .build()
                .encode();

        mockMvc.perform(get(uriComponents.toUri()))
                .andExpect(status().isOk())
                .andReturn();
    }
}