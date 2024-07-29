package com.tastecamp.api.exceptions;

public class RecipeTitleConflictException extends RuntimeException {
    public RecipeTitleConflictException(String message) {
        super(message);
    }
}
