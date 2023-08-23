package ru.practicum.commonData.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleForbiddenException(final ConflictException e) {
        return new ApiError(
                HttpStatus.CONFLICT.toString(),
                "Integrity constraint has been violated.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleForbiddenException(final ConditionsNotMatch e) {
        return new ApiError(
                HttpStatus.CONFLICT.toString(),
                "For the requested operation the conditions are not met.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return new ApiError(
                HttpStatus.NOT_FOUND.toString(),
                "The required object was not found.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBlankException(final MethodArgumentNotValidException e) {
        String field = Objects.requireNonNull(e.getFieldError()).getField();
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                String.format("Field: %s. Error: must not be blank. Value: %s", field, e.getFieldValue(field)));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleonstraintViolationException(final ConstraintViolationException e) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleonstraintValidException(final NotValidException e) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleonstraintValidParamException(final MissingServletRequestParameterException e) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUnauthorizedUser(final Throwable e) {
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "INTERNAL_SERVER_ERROR",
                e.getClass() + " " + e.getMessage());
    }
}
