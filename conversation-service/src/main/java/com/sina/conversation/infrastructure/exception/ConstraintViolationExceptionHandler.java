package com.sina.conversation.infrastructure.exception;

import io.quarkus.logging.Log;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        Log.error(e.getMessage(), e);
        String errorCode = getErrorCodeKey(e.getMessage());
        String localizedErrorDescription = getLocalizedErrorDescription(errorCode, "");

        ErrorRecord errorRecord = ErrorRecord.builder().errorCode(errorCode)
                .message(localizedErrorDescription)
                .httpStatus(Response.Status.BAD_REQUEST.getStatusCode())
                .params(e.getMessage())
                .dateTime(LocalDateTime.now()).build();


        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorRecord)
                .build();
    }

    private String getErrorCodeKey(String errorMessage) {
        String msg = errorMessage;
        String pattern = ":\s*\\{(.*?)\\}";
        String extractedMessageName = "";
        Matcher m = Pattern.compile(pattern).matcher(msg);
        if (m.find()) {
            extractedMessageName = m.group(1);
        }
        return extractedMessageName;
    }

    private String getLocalizedErrorDescription(String errorCode, String param) {
//        try {
            Locale currentLocale = Locale.getDefault();
            ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
            return messages.getString(errorCode);
//        }catch (MissingResourceException ex) {
//            return null;
//        }
    }
}
