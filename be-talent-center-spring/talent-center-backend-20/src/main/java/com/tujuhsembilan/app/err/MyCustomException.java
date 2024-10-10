package com.tujuhsembilan.app.err;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MyCustomException extends RuntimeException {
    public MyCustomException(String message) {
        super(message);
    }
}