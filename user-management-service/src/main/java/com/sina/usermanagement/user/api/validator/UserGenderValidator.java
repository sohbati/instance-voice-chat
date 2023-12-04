package  com.sina.usermanagement.user.api.validator;
import com.sina.usermanagement.user.enumeration.UserGenderEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserGenderValidator implements ConstraintValidator<UserGenderAnnotation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are handled by @NotNull or @Nullable annotations
        } else {
            try {
                UserGenderEnum.valueOf(value.toUpperCase()); // Throws an exception if the value is not a valid general ledger classification
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }
}
