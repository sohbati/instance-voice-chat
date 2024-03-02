package com.sina.conversation.infrastructure;

public class CommonValidation {

    public static final String EMAIL_ADDRESS_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
            + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String USER_NAME_VALID_CHARACTERS = "^[A-Za-z0-9_-]\\w*$";
    public static final String USER_NAME_LENGTH_FIT_PATTERN = "^\\w{5,25}$";
}
