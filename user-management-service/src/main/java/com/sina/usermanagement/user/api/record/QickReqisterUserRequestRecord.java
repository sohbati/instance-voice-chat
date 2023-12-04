package com.sina.usermanagement.user.api.record;

import com.sina.usermanagement.infrastructure.CommonValidation;
import com.sina.usermanagement.user.api.validator.UserGenderAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record QickReqisterUserRequestRecord(
        @NotNull(message = "{VALUE_IS_NULL_OR_EMPTY}")
        @NotBlank(message = "{VALUE_IS_NULL_OR_EMPTY}")
        @Pattern(regexp = CommonValidation.USER_NAME_VALID_CHARACTERS, message = "{USERNAME_CONTAINS_INVALID_CHARACTERS}")
        @Pattern(regexp = CommonValidation.USER_NAME_LENGTH_FIT_PATTERN, message = "{USER_NAME_LENGTH_NOT_FIT}")
        String userName,

        @NotNull(message = "{GENDER_SHOULD_NOT_BE_EMPTY}")
        @NotBlank(message = "{GENDER_SHOULD_NOT_BE_EMPTY}")
        @UserGenderAnnotation
        String gender) {
}
