package com.senla.project.exceptions;

public abstract class BaseException extends RuntimeException {
    public BaseException(String message){
        super();
    }
    public BaseException(String message, Throwable cause){
        super(message, cause);
    }
}
