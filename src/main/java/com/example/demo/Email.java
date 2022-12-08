package com.example.demo;

import lombok.Data;

import java.util.Map;

@Data
public class Email {
    private String to;
    private String from;
    private String subject;
    private String template;
    private Map<String, Object> properties;
}
