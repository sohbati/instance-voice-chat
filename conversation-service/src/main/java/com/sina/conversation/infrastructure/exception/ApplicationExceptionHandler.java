package com.sina.conversation.infrastructure.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<ApplicationException> {
    @Override
    public Response toResponse(ApplicationException e) {

        Locale currentLocale = Locale.getDefault();
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);

        ErrorRecord error = ErrorRecord.builder()
                .message(messages.getString(e.getErrorCode().name()))
                .httpStatus(e.getErrorCode().httpStatus.getStatusCode())
                .errorCode(e.getErrorCode().name())
                .params(e.getParams() != null ? e.getParams().toString(): "")
                .dateTime(LocalDateTime.now())
        .build();

        return Response.status(e.getErrorCode().httpStatus)
                .entity(error)
                .build();
    }
}
