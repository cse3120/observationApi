package com.example.observation.exception;

public class ObservationException extends RuntimeException {

    private String message;

    public ObservationException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}