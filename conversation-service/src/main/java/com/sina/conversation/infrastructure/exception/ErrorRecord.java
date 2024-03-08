package com.sina.conversation.infrastructure.exception;


import java.time.LocalDateTime;

public record ErrorRecord(
        int httpStatus,
        String errorCode,

        String message,
        LocalDateTime dateTime,
        String path,
        String params
) {
    public ErrorRecord(int httpStatus, String errorCode, String message, LocalDateTime dateTime, String path, String params) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
        this.dateTime = dateTime;
        this.path = path;
        this.params = params;
    }
}
