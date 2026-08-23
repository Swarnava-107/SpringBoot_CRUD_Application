package org.example.CRUDSpringBootDemo.dto;

import java.time.LocalDateTime;

public class ExceptionResponseDto {
    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;

    public ExceptionResponseDto(LocalDateTime timestamp, int statusCode, String error, String message, String path) {

    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
