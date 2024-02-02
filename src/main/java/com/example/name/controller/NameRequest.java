package com.example.name.controller;

import com.example.name.entity.Name;
import jakarta.validation.constraints.NotBlank;

public class NameRequest {
    @NotBlank(message = "必須フィールドです")
    private String name;

    private Integer age;

    public Name convertToName() {
        return new Name(null, this.name, this.age);
    }

    public NameRequest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isValid() {

        return name != null && !name.trim().isEmpty();
    }
}
