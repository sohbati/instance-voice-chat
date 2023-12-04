package com.sina.usermanagement.infrastructure.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorRecord(
        int httpStatus,
        String errorCode,

        String message,
        LocalDateTime dateTime,
        String path,
        String params
) {
}
