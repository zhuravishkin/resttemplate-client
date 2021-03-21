package com.zhuravishkin.resttemplateclient;

import com.zhuravishkin.resttemplateclient.config.WebfluxWebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestTemplateClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateClientApplication.class, args);

        WebfluxWebClient webClient = new WebfluxWebClient();
        System.out.println(webClient.getResult());
    }

}
