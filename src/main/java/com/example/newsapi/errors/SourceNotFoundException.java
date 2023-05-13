package com.example.newsapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SourceNotFoundException extends RuntimeException {
    public SourceNotFoundException(String message) {
        super(message);
    }
}
