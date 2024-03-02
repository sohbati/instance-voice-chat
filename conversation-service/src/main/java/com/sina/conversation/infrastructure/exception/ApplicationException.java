package com.sina.conversation.infrastructure.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private ConversationErrorCodeEnum errorCode;
    private String[] params;

}
