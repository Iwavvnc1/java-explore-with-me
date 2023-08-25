package ru.practicum.commonData.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateValidator implements ConstraintValidator<ValidationTime, LocalDateTime> {
    private final LocalDateTime initialReleaseDate = LocalDateTime.now();

    @Override
    public void initialize(ValidationTime validationTime) {
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        return localDateTime != null && localDateTime.isAfter(initialReleaseDate.plusHours(2));
    }
}
