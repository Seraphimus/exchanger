package com.commerce.exchanger.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ExchangeDtoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExchangeDtoAnnotation {
  String message() default "Invalid currency combination";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
}
