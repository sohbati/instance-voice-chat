package com.sina.conversation.infrastructure.exception;


public class ApplicationException extends RuntimeException{
    private ConversationErrorCodeEnum errorCode;
    private String[] params;

    public ConversationErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public String[] getParams() {
        return params;
    }
}
