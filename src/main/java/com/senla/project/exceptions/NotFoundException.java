package com.senla.project.exceptions;

public class NotFoundException extends BaseException{
    public NotFoundException(String resourceName) {
        super(resourceName + " not found");
    }
}
