package com.selfProjectbook.SelfProjecttwoBook.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Locale;

@Data
public class ApiError {
    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus statusCode;
    private String path;

    public ApiError() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError( String error, HttpStatus statusCode, String path) {
        this();
        this.error = error;
        this.statusCode = statusCode;
        this.path=path;
    }


}