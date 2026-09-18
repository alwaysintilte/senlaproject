package com.senla.project.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), "Already exists");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidRequestDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestDataException(InvalidRequestDataException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(e.getStatus().value(), e.getMessage(), "Bad request");
        return new ResponseEntity<>(response, e.getStatus());
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), "Not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
