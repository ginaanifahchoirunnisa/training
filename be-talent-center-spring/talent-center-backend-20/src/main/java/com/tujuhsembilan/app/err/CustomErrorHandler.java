package com.tujuhsembilan.app.err;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle500Error(Exception ex) {
        // Customize the error message here
        String errorMessage = "Internal Server Error: " + ex.getMessage();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorMessage);
    }
}