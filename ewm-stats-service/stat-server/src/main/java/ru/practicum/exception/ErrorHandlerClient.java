package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorHandlerClient {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorClient handleBlankException(final NotValidException e) {
        return new ApiErrorClient(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                e.getMessage());
    }
}
