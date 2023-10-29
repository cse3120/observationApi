package com.example.observation.exception;

import com.example.observation.mapper.ObservationMapper;
import com.example.observation.response.ErrorInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = ObservationException.class)
    public ResponseEntity<ErrorInfoResponse> observationException(ObservationException ex) {
        return ResponseEntity.badRequest().body(ObservationMapper.getErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(value = PostgreSQLException.class)
    public ResponseEntity<ErrorInfoResponse> postgresSqlException(PostgreSQLException ex) {
        return ResponseEntity.internalServerError().body(ObservationMapper.getErrorResponse("PostgreSQL-related error is occurred", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }


    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (Objects.requireNonNull(ex.getMessage()).contains("JSON parse error: Cannot deserialize value of type `java.time.LocalDateTime")) {
            return ResponseEntity.badRequest().body("Invalid 'observationDateTime' format in the JSON request payload. Please use the format 'yyyy-MM-dd'T'HH:mm:ss'.");
        } else if (Objects.requireNonNull(ex.getMessage()).contains("ObservationType is not available")) {
            return ResponseEntity.badRequest().body(ObservationMapper.getErrorResponse("Invalid 'observationType' value", HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.badRequest().body(ObservationMapper.getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()));
    }
}