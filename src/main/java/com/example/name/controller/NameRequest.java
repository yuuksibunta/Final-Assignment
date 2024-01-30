package com.example.name.controller;

import jakarta.validation.constraints.NotBlank;

public class NameRequest {
    @NotBlank(message = "必須フィールドです")
    private String name;

    private int age;

    public NameRequest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isValid() {

        return name != null && !name.trim().isEmpty();
    }
}
