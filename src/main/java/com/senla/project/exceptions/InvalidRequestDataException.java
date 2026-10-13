package com.senla.project.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidRequestDataException extends BaseException{
    private HttpStatus status;
    public InvalidRequestDataException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public InvalidRequestDataException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
