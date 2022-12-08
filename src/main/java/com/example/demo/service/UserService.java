package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    public RestTemplate restTemplate;
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        String data = restTemplate.postForObject("http://localhost:8082/v1/customer/load", username ,String.class);
            Map map = new ObjectMapper().readValue(data, Map.class);
        return (UserDetails) map;
    }
}
