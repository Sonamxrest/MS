package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Users
{
    @JsonProperty("name")
    String name;
    String email;
    String otp;
}
