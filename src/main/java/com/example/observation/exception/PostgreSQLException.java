package com.example.observation.exception;

public class PostgreSQLException extends RuntimeException {

    private String message;

    public PostgreSQLException(String message) {
        super(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
