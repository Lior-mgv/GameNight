package org.lior.gamenight.Validation.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.lior.gamenight.Validation.Annotations.EitherOr;
import org.springframework.beans.BeanWrapperImpl;

public class EitherOrValidator implements ConstraintValidator<EitherOr, Object> {
    private String firstFieldName;
    private String secondFieldName;
    @Override
    public void initialize(EitherOr constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.firstField();
        this.secondFieldName = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        boolean firstFieldValue;
        boolean secondFieldValue;

        firstFieldValue = (boolean) beanWrapper.getPropertyValue(firstFieldName);
        secondFieldValue = (boolean) beanWrapper.getPropertyValue(secondFieldName);

        return !firstFieldValue || !secondFieldValue;
    }
}
