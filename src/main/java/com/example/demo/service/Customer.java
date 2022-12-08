package com.example.demo.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class Customer  implements UserDetails {

    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;


    private Double balance;

    private String profile;

    private String username;
    private String password;
    private Object role;

    private List<Object> accounts;

    private Object certificates;

    private Double totalBalance = 0d;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return  ObjectUtils.isEmpty(role) ? new ArrayList<>() : Arrays.asList(new SimpleGrantedAuthority("ROLE_"+role.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
