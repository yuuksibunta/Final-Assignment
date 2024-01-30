package com.example.name.controller;

public class NameResponse {
    private final String message;

    public NameResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
