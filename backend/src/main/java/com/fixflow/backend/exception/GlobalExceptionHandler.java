package com.fixflow.backend.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException exception
    ) {

        String message = exception.getMessage();

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if ("User not found".equals(message)
                || "Issue not found".equals(message)) {

            status = HttpStatus.NOT_FOUND;

        } else if ("You do not have access to this issue".equals(message)
                || "Only the issue creator can delete this issue".equals(message)) {

            status = HttpStatus.FORBIDDEN;

        } else if ("Email already registered".equals(message)) {

            status = HttpStatus.CONFLICT;

        } else if ("Assigned user not found".equals(message)
                || "Selected user is not an AGENT".equals(message)) {

            status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}