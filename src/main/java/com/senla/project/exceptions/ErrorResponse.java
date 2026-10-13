package com.senla.project.exceptions;

public record ErrorResponse(
        int status,
        String message,
        String error
) {
}
