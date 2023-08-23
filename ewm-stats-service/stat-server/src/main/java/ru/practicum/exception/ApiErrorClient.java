package ru.practicum.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiErrorClient {
    private String message;
    private String reason;
    private String status;
    private String timestamp;

    public ApiErrorClient(String status, String reason, String message) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(dateTimeFormatter);
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

    public String getTimestamp() {
        return timestamp;
    }
}
