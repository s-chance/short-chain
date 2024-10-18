package org.entropy.shortchain.annotation.validated;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.entropy.shortchain.validation.LinkStateValidation;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {LinkStateValidation.class}
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StateValidated {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
