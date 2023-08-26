package ru.practicum.commonData.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import ru.practicum.commonData.utils.FormatDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ApiError {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FormatDate.TIME_FORMAT);
    private String message;
    private String reason;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatDate.TIME_FORMAT)
    private LocalDateTime timestamp;

    public ApiError(String status, String reason, String message) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
