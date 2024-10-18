package org.entropy.shortchain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.entropy.shortchain.annotation.validated.StateValidated;

import static org.entropy.shortchain.constant.LinkState.ACTIVE;
import static org.entropy.shortchain.constant.LinkState.DISABLED;

public class LinkStateValidation implements ConstraintValidator<StateValidated, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return ACTIVE.name().equals(s) || DISABLED.name().equals(s);
    }
}
