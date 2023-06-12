package peaksoft.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;


import java.lang.annotation.*;
import java.util.List;


/**
 * @author kurstan
 * @created at 23.03.2023 12:24
 */

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {
    String message() default "Invalid password";
    Class<?>[]groups()default {};
    Class<? extends Payload>[] payload() default {};
}
