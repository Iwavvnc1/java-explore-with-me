package ru.practicum.commonData.exceptions;

public class ConditionsNotMatch extends RuntimeException {
    public ConditionsNotMatch(String message) {
        super(message);
    }

    public ConditionsNotMatch(String message, Throwable cause) {
        super(message, cause);
    }
}
