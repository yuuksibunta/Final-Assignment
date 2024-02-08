package com.example.name.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public  class NameBadRequestException extends RuntimeException {
    public NameBadRequestException(String message) {
        super(message);
    }
}