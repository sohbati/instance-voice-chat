package com.sina.usermanagement.user.enumeration;

import jakarta.ws.rs.core.Response;

public enum UserErrorCodeEnum {

    USER_NAME_ALREADY_EXISTS(Response.Status.BAD_REQUEST),
    USER_NAME_NOT_FOUND(Response.Status.NOT_FOUND),
    USER_NOT_FOUND(Response.Status.NOT_FOUND),

    EMAIL_IS_NOT_VALID(Response.Status.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(Response.Status.BAD_REQUEST),
    VALUE_IS_NULL_OR_EMPTY(Response.Status.BAD_REQUEST),
    USERNAME_CONTAINS_INVALID_CHARACTERS(Response.Status.BAD_REQUEST),
    USER_NAME_LENGTH_NOT_FIT(Response.Status.BAD_REQUEST),
    FULL_NAME_SHOULD_NOT_BE_EMPTY(Response.Status.BAD_REQUEST),
    GENDER_SHOULD_NOT_BE_EMPTY(Response.Status.BAD_REQUEST),
    GENDER_IS_INVALID(Response.Status.BAD_REQUEST);

    public final Response.Status httpStatus;

    private UserErrorCodeEnum(Response.Status status) {
        this.httpStatus = status;
    }
}
