package ru.practicum.commonData.exceptions;

public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }

    public NotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
