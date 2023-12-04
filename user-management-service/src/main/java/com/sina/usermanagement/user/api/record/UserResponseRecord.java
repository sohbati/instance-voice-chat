package com.sina.usermanagement.user.api.record;

import com.sina.usermanagement.infrastructure.CommonValidation;
import com.sina.usermanagement.user.api.validator.UserGenderAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserResponseRecord(
        String id,
        String userName,
        String fullName,
        String email,
        String gender
) {
}
