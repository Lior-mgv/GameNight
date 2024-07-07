package org.lior.gamenight.Validation.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.lior.gamenight.Validation.Validators.EitherOrValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EitherOrValidator.class})
public @interface EitherOr {

    String message() default "Values are mutually exclusive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String firstField();
    String secondField();

}