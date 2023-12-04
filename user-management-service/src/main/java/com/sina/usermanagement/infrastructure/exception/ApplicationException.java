package com.sina.usermanagement.infrastructure.exception;

import com.sina.usermanagement.user.enumeration.UserErrorCodeEnum;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private UserErrorCodeEnum errorCode;
    private String[] params;
    public ApplicationException(UserErrorCodeEnum userErrorCodeEnum) {
        this.errorCode = userErrorCodeEnum;
    }

    public ApplicationException(UserErrorCodeEnum userErrorCodeEnum, String... params) {
        this.errorCode = userErrorCodeEnum;
        this.params = params;
    }

    public static ApplicationException emailNotValidException(String email) {
        throw new ApplicationException(UserErrorCodeEnum.EMAIL_IS_NOT_VALID, email);
    }

    public static ApplicationException stringValueIsEmpty(String parameter) {
        throw new ApplicationException(UserErrorCodeEnum.VALUE_IS_NULL_OR_EMPTY, parameter);
    }

    public static ApplicationException userNameNotFoundException(String userName) {
        throw new ApplicationException(UserErrorCodeEnum.USER_NAME_NOT_FOUND);
    }

    public static ApplicationException userNameAlreadyExist(String userName) {
        throw new ApplicationException(UserErrorCodeEnum.USER_NAME_ALREADY_EXISTS);
    }

    public static ApplicationException emailAlreadyExists(String email) {
        throw new ApplicationException(UserErrorCodeEnum.EMAIL_ALREADY_EXISTS, email);
    }


    public static ApplicationException userNameContainsInvalidCharacters(String userName) {
        throw new ApplicationException(UserErrorCodeEnum.USERNAME_CONTAINS_INVALID_CHARACTERS, userName);

    }
}
