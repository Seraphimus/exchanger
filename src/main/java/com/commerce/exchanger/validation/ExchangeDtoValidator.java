package com.commerce.exchanger.validation;

import com.commerce.exchanger.dto.ExchangeDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExchangeDtoValidator implements
    ConstraintValidator<ExchangeDtoAnnotation, ExchangeDto> {

  @Override
  public boolean isValid(ExchangeDto value, ConstraintValidatorContext context) {
    return !value.getFromCurrency().equalsIgnoreCase(value.getToCurrency());
  }
}
