package ru.practicum.commonData.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({ConflictException.class, ConditionsNotMatch.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleForbiddenException(final RuntimeException e) {
        log.debug("Получен статус {} {}", HttpStatus.CONFLICT, e.getMessage(),e);
        return new ApiError(
                HttpStatus.CONFLICT.toString(),
                "Integrity constraint has been violated.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.debug("Получен статус {} {}", HttpStatus.NOT_FOUND, e.getMessage(),e);
        return new ApiError(
                HttpStatus.NOT_FOUND.toString(),
                "The required object was not found.",
                e.getMessage());
    }


    @ExceptionHandler({ConstraintViolationException.class, NotValidException.class, ValidationException.class,
            MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleonstraintViolationException(final Exception e) {
        log.debug("Получен статус {} {}", HttpStatus.BAD_REQUEST, e.getMessage(),e);
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUnauthorizedUser(final Throwable e) {
        log.debug("Получен статус {} {}", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),e);
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "INTERNAL_SERVER_ERROR",
                e.getClass() + " " + e.getMessage());
    }
}

