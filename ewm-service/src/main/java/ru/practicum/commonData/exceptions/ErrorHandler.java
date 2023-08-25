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
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleForbiddenException(final ConflictException e) {
        log.debug("Получен статус {} {}", HttpStatus.CONFLICT, e.getMessage(),e);
        return new ApiError(
                HttpStatus.CONFLICT.toString(),
                "Integrity constraint has been violated.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleForbiddenException(final ConditionsNotMatch e) {
        log.debug("Получен статус {} {}", HttpStatus.CONFLICT, e.getMessage(),e);
        return new ApiError(
                HttpStatus.CONFLICT.toString(),
                "For the requested operation the conditions are not met.",
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBlankException(final MethodArgumentNotValidException e) {
        log.debug("Получен статус {} {}", HttpStatus.BAD_REQUEST, e.getMessage(),e);
        String field = Objects.requireNonNull(e.getFieldError()).getField();
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                String.format("Field: %s. Error: must not be blank. Value: %s", field, e.getFieldValue(field)));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleonstraintViolationException(final ConstraintViolationException e) {
        log.debug("Получен статус {} {}", HttpStatus.BAD_REQUEST, e.getMessage(),e);
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleonstraintValidException(final NotValidException e) {
        log.debug("Получен статус {} {}", HttpStatus.BAD_REQUEST, e.getMessage(),e);
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleonstraintValidParamException(final MissingServletRequestParameterException e) {
        log.debug("Получен статус {} {}", HttpStatus.BAD_REQUEST, e.getMessage(),e);
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleonstraintValidationException(final ValidationException e) {
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

