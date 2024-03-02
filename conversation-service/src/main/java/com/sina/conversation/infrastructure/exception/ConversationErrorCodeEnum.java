package com.sina.conversation.infrastructure.exception;

import jakarta.ws.rs.core.Response;

public enum ConversationErrorCodeEnum {

    TEST_ALREADY_EXISTS(Response.Status.BAD_REQUEST);

    public final Response.Status httpStatus;

    private ConversationErrorCodeEnum(Response.Status status) {
        this.httpStatus = status;
    }
}
