package ru.practicum.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.model.FormatDate;

import java.time.LocalDateTime;

public class ApiErrorClient {
    private String message;
    private String reason;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatDate.TIME_FORMAT)
    private LocalDateTime timestamp;

    public ApiErrorClient(String status, String reason, String message) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
