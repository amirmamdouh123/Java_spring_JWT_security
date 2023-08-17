package com.example.demo.errors;

import java.util.Date;

public class ErrorDto {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDto(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}