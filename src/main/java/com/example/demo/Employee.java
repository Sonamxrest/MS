package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
    private int id;
    @JsonProperty("employee_name")
    private String employeeName;
    @JsonProperty("employee_salary")
    private String employeeSalary;
    @JsonProperty("employee_age")
    private String employeeAge;
    @JsonProperty("profile_image")
    private String profileImage;
}
