package com.sina.conversation.infrastructure.exception;

import jakarta.ws.rs.core.Response;

public enum ErrorCodeEnum {

    JSON_DECODE_EXCEPTION(Response.Status.INTERNAL_SERVER_ERROR);

    public final Response.Status httpStatus;

    private ErrorCodeEnum(Response.Status status) {
        this.httpStatus = status;
    }
}
