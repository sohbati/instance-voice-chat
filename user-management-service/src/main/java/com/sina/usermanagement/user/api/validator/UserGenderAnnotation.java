package  com.sina.usermanagement.user.api.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserGenderValidator.class)
public @interface UserGenderAnnotation {
    String message() default "{GENDER_IS_INVALID}";
    Class<?>[] groups() default {};
    Class[] payload() default {};
}

