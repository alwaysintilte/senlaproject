package com.senla.project.exceptions;

public class AlreadyExistsException extends BaseException {
    public AlreadyExistsException(String resourceName) {
        super(resourceName + " already exists");
    }
}
