package com.zhuravishkin.resttemplateclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @JsonProperty("tuple_name")
    private String name;

    @JsonProperty("tuple_age")
    private int age;
}
