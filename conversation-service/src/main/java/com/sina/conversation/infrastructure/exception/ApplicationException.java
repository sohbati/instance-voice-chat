package com.sina.conversation.infrastructure.exception;


public class ApplicationException extends RuntimeException{
    private final ErrorCodeEnum errorCode;
    private final String[] params;

    public ApplicationException(ErrorCodeEnum errorCode, Exception ex, String...params) {
        super(ex.getMessage(), ex);
        this.errorCode = errorCode;
        this.params = params;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public String[] getParams() {
        return params;
    }
}
