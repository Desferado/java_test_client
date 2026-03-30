package com.denis.java_test_client.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public static void throwIfNull(Object object, String errorMessage) {
        if (object == null) {
            throw new EntityNotFoundException(errorMessage);
        }
    }

    public static void throwIfFalse(boolean condition, String errorMessage) {
        if (!condition) {
            throw new EntityNotFoundException(errorMessage);
        }
    }

    // Альтернативный вариант через ResponseStatusException для удобства
    public static void throwHttpNotFound(String errorMessage) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
    }

}